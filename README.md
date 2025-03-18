Hello and welcome to the Dryad engine FOSS project. Where we aim to Create our own roots and escape the never ending sea of greed and negativity surounding our belloved projects.
<hr>
This engine provides the means to create entire games, programs, or even operating systems if youre insane enough withough lifting your fingers off the keyboard, once. (in theory)
This means that you can create, delete and manage files, inspect cpu's and threads, see documentation, install new software from the cloud, download files, run system commands (as
in operating system) and much more.
<hr>
It also has its own built in programming language for the shell called Arrow, coming with the built in language interpreter Quil (TBD). Of course, with a little bit of care from either
me or the community, you can use the languange with any other languange like C, Python, JavaScript, etc. As long as you have its interpreter (TBD).
<hr>
As for the rendering API, it provides OpenGL (TBD), WebGl (TBD), Vulkan (TBD), and DirectX11 and 12 (TBD). (For now, the only rendering api it has is SDL2, but i plan to completely
deprecate it and replace it for OpenGL with version 0.5.0)
<h1>How to install it (version 0.2.4)</h1>
To install and use it, all you have to do is download the repository, put it somwhere, and go into data/batch if you're on windows, or data/bun if your're on linux or ios, then run the
BuildAndRun script, and bam! You've just compiled your project and started debugging it.
<br>
You can go trough the code in src, and look around, familiarize yourself with the commands, and the engine as a whole, and if you want to help out with this project you can join our
discor (TBD) and contribute. Or, if you find any bugs or want to give some feedback, you can submit to this form, and most likely the bug will be resolved in the next snaphot or release
of the engine. Since i hate buggy unresponsive or straight up unreliable code, you can count on me that any bug you may find while using the engine will eb resolved if not right away,
soon.
<h1>Documentation (version 0.2.4)</h1>
Most of the project is undocumented for now since i have to redo most of it, and since i dont really know how to use JavaDoc just yet.<br>
The projects you make cant be exported just yet, because im too stupid and don't understand Launch4J<br>
You allready have the project set up with maven, so you dont have to do anything else. If you'd like to add compatibility with Gradle put it on the discord and most likely i will add
an option for it too.<br>
Theres a testing system implemented, allbeit crude and outdated (ill update it once im done with the shell programming) because nvim doesnt really have any compatibility with JUnit Test 
and i couldnt be bother at the time to fix it, or dig deeper into why it broke, so instead i just reused some code fot the testing enviorment, which really soon you will be able to
compile and run directly from the terminal, with no need for a second batch file, or special test folder with special test CommandManagers and all.
