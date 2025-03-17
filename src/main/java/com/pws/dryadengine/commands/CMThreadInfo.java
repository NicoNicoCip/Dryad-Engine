package com.pws.dryadengine.commands;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.pws.dryadengine.core.commands.Command;
import com.pws.dryadengine.func.Debug;

/**
 * Command that displays information about all threads in the JVM.
 * Provides detailed information about thread states, CPU usage, and stack traces.
 */
public class CMThreadInfo extends Command {
    private boolean showAll = false;
    private boolean showActive = false;
    private boolean showBlocked = false;
    private boolean showWaiting = false;
    private boolean showTimedWaiting = false;
    private boolean showCpuTime = false;
    private boolean showStackTrace = false;
    private boolean sortByName = false;
    private boolean showHelp = false;

    public CMThreadInfo() {
        this.command = "threads";
        this.options = new String[]{
            "-h", "--help",
            "-a", "--all",
            "-r", "--active",
            "-b", "--blocked",
            "-w", "--waiting",
            "-t", "--timed",
            "-c", "--cpu",
            "-s", "--stack",
            "-n", "--sort"
        };
        setHelp();
        construct();
    }

    @Override
    public void setHelp() {
        String intro = "threads - Display information about JVM threads";
        String name = "threads";
        String nameInfo = "- displays detailed information about all threads in the JVM";
        String synopsis = "threads [OPTIONS]";
        
        List<String> descElements = Arrays.asList(
            "-h or --help", 
            "-a or --all", 
            "-r or --active", 
            "-b or --blocked", 
            "-w or --waiting", 
            "-t or --timed", 
            "-c or --cpu", 
            "-s or --stack", 
            "-n or --sort"
        );
        
        List<String> descInfo = Arrays.asList(
            "Display this help information",
            "Shows all threads including system threads (default if no filter specified)",
            "Shows only threads in RUNNABLE state",
            "Shows only threads in BLOCKED state",
            "Shows only threads in WAITING state",
            "Shows only threads in TIMED_WAITING state",
            "Shows CPU time consumed by each thread",
            "Shows the stack trace for each thread (useful for debugging)",
            "Sorts threads alphabetically by name for easier reading"
        );
        
        String descFooter = "If no state filter is specified (-r, -b, -w, -t), all threads will be shown. Multiple filters can be combined.";
        String author = "Dryad Engine Team";
        String license = "Copyright © 2023 Free Software Foundation, Inc. License GPLv3+: GNU GPL version 3 or later"+
          " <https://gnu.org/licenses/gpl.html>. This is free software: you are free to change and redistribute it."+
          " There is NO WARRANTY, to the extent permitted by law.";
        
        this.help = new CIHelp(intro, name, nameInfo, synopsis, descElements, descInfo, descFooter, author, license);
    }

    @Override
    public void construct() {
        // Help option
        registerOptions((args) -> showHelp = true, "-h", "--help");
        
        // Thread filtering options
        registerOptions((args) -> showAll = true, "-a", "--all");
        registerOptions((args) -> showActive = true, "-r", "--active");
        registerOptions((args) -> showBlocked = true, "-b", "--blocked");
        registerOptions((args) -> showWaiting = true, "-w", "--waiting");
        registerOptions((args) -> showTimedWaiting = true, "-t", "--timed");
        
        // Additional information options
        registerOptions((args) -> showCpuTime = true, "-c", "--cpu");
        registerOptions((args) -> showStackTrace = true, "-s", "--stack");
        registerOptions((args) -> sortByName = true, "-n", "--sort");
    }

    @Override
    public boolean run(String[] args) {
        // Reset flags for each run
        showAll = false;
        showActive = false;
        showBlocked = false;
        showWaiting = false;
        showTimedWaiting = false;
        showCpuTime = false;
        showStackTrace = false;
        sortByName = false;
        showHelp = false;
        
        // Process options
        runOptions(args);
        
        // Show help if requested
        if (showHelp || args.length == 0) {
            help.print();
            return true;
        }
        
        // If no specific state filter is set, show all threads
        if (!showActive && !showBlocked && !showWaiting && !showTimedWaiting) {
            showAll = true;
        }
        
        // Get thread information
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds, showStackTrace ? Integer.MAX_VALUE : 0);
        
        // Print header
        Debug.println("\n=== Thread Information ===");
        Debug.println("Total threads: " + threadIds.length);
        Debug.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        Debug.println("==============================");
        
        // Sort threads if requested
        Map<String, ThreadInfo> sortedThreads = new TreeMap<>();
        if (sortByName) {
            for (ThreadInfo info : threadInfos) {
                if (info != null) {
                    sortedThreads.put(info.getThreadName(), info);
                }
            }
        }
        
        // Display thread information
        if (sortByName) {
            for (Map.Entry<String, ThreadInfo> entry : sortedThreads.entrySet()) {
                displayThreadInfo(threadMXBean, entry.getValue());
            }
        } else {
            for (ThreadInfo info : threadInfos) {
                if (info != null) {
                    displayThreadInfo(threadMXBean, info);
                }
            }
        }
        
        return true;
    }
    
    private void displayThreadInfo(ThreadMXBean threadMXBean, ThreadInfo info) {
        Thread.State state = info.getThreadState();
        
        // Filter based on thread state if filters are active
        if (!showAll && 
            !(showActive && state == Thread.State.RUNNABLE) &&
            !(showBlocked && state == Thread.State.BLOCKED) &&
            !(showWaiting && state == Thread.State.WAITING) &&
            !(showTimedWaiting && state == Thread.State.TIMED_WAITING)) {
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("\nThread ID: ").append(info.getThreadId())
          .append("\nName: ").append(info.getThreadName())
          .append("\nState: ").append(state);
        
        // Try to get thread object to get more information
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getId() == info.getThreadId()) {
                sb.append("\nPriority: ").append(t.getPriority())
                  .append("\nDaemon: ").append(t.isDaemon());
                
                ThreadGroup group = t.getThreadGroup();
                if (group != null) {
                    sb.append("\nThread Group: ").append(group.getName());
                }
                break;
            }
        }
        
        if (showCpuTime) {
            long cpuTimeNanos = threadMXBean.getThreadCpuTime(info.getThreadId());
            if (cpuTimeNanos != -1) {
                long cpuTimeMs = TimeUnit.NANOSECONDS.toMillis(cpuTimeNanos);
                sb.append("\nCPU time: ").append(cpuTimeMs).append(" ms");
                
                // Add CPU usage percentage if possible
                long upTimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
                if (upTimeMs > 0) {
                    double cpuUsage = ((double) cpuTimeMs / upTimeMs) * 100.0;
                    sb.append(" (").append(String.format("%.2f", cpuUsage)).append("% of uptime)");
                }
            }
        }
        
        if (info.getLockName() != null) {
            sb.append("\nLock: ").append(info.getLockName());
            if (info.getLockOwnerName() != null) {
                sb.append("\nLock owner: ").append(info.getLockOwnerName())
                  .append(" (id=").append(info.getLockOwnerId()).append(")");
            }
        }
        
        // Add blocked/waited counts if available
        sb.append("\nBlocked count: ").append(info.getBlockedCount());
        sb.append("\nWaited count: ").append(info.getWaitedCount());
        
        Debug.println(sb.toString());
        
        if (showStackTrace) {
            StackTraceElement[] stackTrace = info.getStackTrace();
            if (stackTrace.length > 0) {
                Debug.println("Stack trace:");
                for (StackTraceElement element : stackTrace) {
                    Debug.println("    " + element.toString());
                }
            } else {
                Debug.println("Stack trace: [empty]");
            }
        }
        
        Debug.println("------------------------------");
    }
}
