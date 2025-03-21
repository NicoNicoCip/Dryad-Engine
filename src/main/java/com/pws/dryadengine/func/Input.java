package com.pws.dryadengine.func;

import static io.github.libsdl4j.api.keycode.SdlKeycode.SDL_SCANCODE_TO_KEYCODE;
import static io.github.libsdl4j.api.scancode.SDL_Scancode.*;

import java.util.HashMap;

public abstract class Input {

    public static HashMap<Integer, State> keys = new HashMap<>();

    public static void addKey(int code, State state) {
        keys.put(code, state);
    }

    public static boolean checkKey(int code, State state) {
        if(keys.get(code) == null) {
            return false;
        } 

        State in = keys.get(code);
        State out = null;

        if(in == State.pressed) {
            keys.put(code, State.held);
            out = State.pressed;
        } else if(in == State.held) {
            out = State.held;
        }
        else if(in == State.released) {
            keys.remove(code);
            out = State.released;
        }

        if(out == state)
            return true;
        else
            return false;
    }

    public static enum State {
        pressed,
        held,
        released
    }

    public static final int _none = 0;
    public static final int _return = '\r';
    public static final int _escape = '\u001B';
    public static final int _backspace = '\b';
    public static final int _tab = '\t';
    public static final int _space = ' ';
    public static final int _exclaim = '!';
    public static final int _quotedbl = '"';
    public static final int _hash = '#';
    public static final int _percent = '%';
    public static final int _dollar = '$';
    public static final int _ampersand = '&';
    public static final int _quote = '\'';
    public static final int _leftparen = '(';
    public static final int _rightparen = ')';
    public static final int _asterisk = '*';
    public static final int _plus = '+';
    public static final int _comma = ';';
    public static final int _minus = '-';
    public static final int _period = '.';
    public static final int _slash = '/';
    public static final int _0 = '0';
    public static final int _1 = '1';
    public static final int _2 = '2';
    public static final int _3 = '3';
    public static final int _4 = '4';
    public static final int _5 = '5';
    public static final int _6 = '6';
    public static final int _7 = '7';
    public static final int _8 = '8';
    public static final int _9 = '9';
    public static final int _colon = ':';
    public static final int _semicolon = ';';
    public static final int _less = '<';
    public static final int _equals = '=';
    public static final int _greater = '>';
    public static final int _question = '?';
    public static final int _at = '@';
    
    public static final int _leftbracket = '[';
    public static final int _backslash = '\\';
    public static final int _rightbracket = ']';
    public static final int _caret = '^';
    public static final int _underscore = '_';
    public static final int _backquote = '`';
    public static final int _a = 'a';
    public static final int _b = 'b';
    public static final int _c = 'c';
    public static final int _d = 'd';
    public static final int _e = 'e';
    public static final int _f = 'f';
    public static final int _g = 'g';
    public static final int _h = 'h';
    public static final int _i = 'i';
    public static final int _j = 'j';
    public static final int _k = 'k';
    public static final int _l = 'l';
    public static final int _m = 'm';
    public static final int _n = 'n';
    public static final int _o = 'o';
    public static final int _p = 'p';
    public static final int _q = 'q';
    public static final int _r = 'r';
    public static final int _s = 's';
    public static final int _t = 't';
    public static final int _u = 'u';
    public static final int _v = 'v';
    public static final int _w = 'w';
    public static final int _x = 'x';
    public static final int _y = 'y';
    public static final int _z = 'z';
    
    public static final int _capslock = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CAPSLOCK);
    
    public static final int _f1 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F1);
    public static final int _f2 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F2);
    public static final int _f3 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F3);
    public static final int _f4 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F4);
    public static final int _f5 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F5);
    public static final int _f6 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F6);
    public static final int _f7 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F7);
    public static final int _f8 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F8);
    public static final int _f9 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F9);
    public static final int _f10 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F10);
    public static final int _f11 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F11);
    public static final int _f12 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F12);
    
    public static final int _printscreen = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PRINTSCREEN);
    public static final int _scrolllock = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SCROLLLOCK);
    public static final int _pause = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PAUSE);
    public static final int _insert = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_INSERT);
    public static final int _home = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_HOME);
    public static final int _pageup = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PAGEUP);
    public static final int _delete = '\177';
    public static final int _end = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_END);
    public static final int _pagedown = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PAGEDOWN);
    public static final int _right = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RIGHT);
    public static final int _left = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_LEFT);
    public static final int _down = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_DOWN);
    public static final int _up = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_UP);
    
    public static final int _numlockclear = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_NUMLOCKCLEAR);
    public static final int _kp_divide = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_DIVIDE);
    public static final int _kp_multiply = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MULTIPLY);
    public static final int _kp_minus = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MINUS);
    public static final int _kp_plus = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_PLUS);
    public static final int _kp_enter = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_ENTER);
    public static final int _kp_1 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_1);
    public static final int _kp_2 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_2);
    public static final int _kp_3 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_3);
    public static final int _kp_4 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_4);
    public static final int _kp_5 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_5);
    public static final int _kp_6 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_6);
    public static final int _kp_7 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_7);
    public static final int _kp_8 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_8);
    public static final int _kp_9 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_9);
    public static final int _kp_0 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_0);
    public static final int _kp_period = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_PERIOD);
    
    public static final int _application = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_APPLICATION);
    public static final int _power = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_POWER);
    public static final int _kp_equals = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_EQUALS);
    public static final int _f13 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F13);
    public static final int _f14 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F14);
    public static final int _f15 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F15);
    public static final int _f16 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F16);
    public static final int _f17 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F17);
    public static final int _f18 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F18);
    public static final int _f19 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F19);
    public static final int _f20 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F20);
    public static final int _f21 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F21);
    public static final int _f22 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F22);
    public static final int _f23 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F23);
    public static final int _f24 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_F24);
    public static final int _execute = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_EXECUTE);
    public static final int _help = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_HELP);
    public static final int _menu = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_MENU);
    public static final int _select = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SELECT);
    public static final int _stop = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_STOP);
    public static final int _again = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AGAIN);
    public static final int _undo = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_UNDO);
    public static final int _cut = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CUT);
    public static final int _copy = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_COPY);
    public static final int _paste = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PASTE);
    public static final int _find = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_FIND);
    public static final int _mute = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_MUTE);
    public static final int _volumeup = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_VOLUMEUP);
    public static final int _volumedown = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_VOLUMEDOWN);
    public static final int _kp_comma = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_COMMA);
    public static final int _kp_equalsas400 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_EQUALSAS400);
    
    public static final int _alterase = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_ALTERASE);
    public static final int _sysreq = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SYSREQ);
    public static final int _cancel = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CANCEL);
    public static final int _clear = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CLEAR);
    public static final int _prior = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_PRIOR);
    public static final int _return2 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RETURN2);
    public static final int _separator = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SEPARATOR);
    public static final int _out = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_OUT);
    public static final int _oper = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_OPER);
    public static final int _clearagain = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CLEARAGAIN);
    public static final int _crsel = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CRSEL);
    public static final int _exsel = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_EXSEL);
    
    public static final int _kp_00 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_00);
    public static final int _kp_000 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_000);
    public static final int _thousandsseparator = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_THOUSANDSSEPARATOR);
    public static final int _decimalseparator = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_DECIMALSEPARATOR);
    public static final int _currencyunit = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CURRENCYUNIT);
    public static final int _currencysubunit = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CURRENCYSUBUNIT);
    public static final int _kp_leftparen = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_LEFTPAREN);
    public static final int _kp_rightparen = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_RIGHTPAREN);
    public static final int _kp_leftbrace = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_LEFTBRACE);
    public static final int _kp_rightbrace = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_RIGHTBRACE);
    public static final int _kp_tab = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_TAB);
    public static final int _kp_backspace = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_BACKSPACE);
    public static final int _kp_a = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_A);
    public static final int _kp_b = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_B);
    public static final int _kp_c = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_C);
    public static final int _kp_d = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_D);
    public static final int _kp_e = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_E);
    public static final int _kp_f = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_F);
    public static final int _kp_xor = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_XOR);
    public static final int _kp_power = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_POWER);
    public static final int _kp_percent = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_PERCENT);
    public static final int _kp_less = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_LESS);
    public static final int _kp_greater = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_GREATER);
    public static final int _kp_ampersand = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_AMPERSAND);
    public static final int _kp_dblampersand = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_DBLAMPERSAND);
    public static final int _kp_verticalbar = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_VERTICALBAR);
    public static final int _kp_dblverticalbar = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_DBLVERTICALBAR);
    public static final int _kp_colon = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_COLON);
    public static final int _kp_hash = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_HASH);
    public static final int _kp_space = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_SPACE);
    public static final int _kp_at = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_AT);
    public static final int _kp_exclam = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_EXCLAM);
    public static final int _kp_memstore = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMSTORE);
    public static final int _kp_memrecall = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMRECALL);
    public static final int _kp_memclear = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMCLEAR);
    public static final int _kp_memadd = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMADD);
    public static final int _kp_memsubtract = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMSUBTRACT);
    public static final int _kp_memmultiply = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMMULTIPLY);
    public static final int _kp_memdivide = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_MEMDIVIDE);
    public static final int _kp_plusminus = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_PLUSMINUS);
    public static final int _kp_clear = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_CLEAR);
    public static final int _kp_clearentry = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_CLEARENTRY);
    public static final int _kp_binary = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_BINARY);
    public static final int _kp_octal = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_OCTAL);
    public static final int _kp_decimal = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_DECIMAL);
    public static final int _kp_hexadecimal = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KP_HEXADECIMAL);
    
    public static final int _lctrl = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_LCTRL);
    public static final int _lshift = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_LSHIFT);
    public static final int _lalt = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_LALT);
    public static final int _lgui = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_LGUI);
    public static final int _rctrl = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RCTRL);
    public static final int _rshift = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RSHIFT);
    public static final int _ralt = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RALT);
    public static final int _rgui = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_RGUI);
    
    public static final int _mode = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_MODE);
    
    public static final int _audionext = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIONEXT);
    public static final int _audioprev = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOPREV);
    public static final int _audiostop = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOSTOP);
    public static final int _audioplay = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOPLAY);
    public static final int _audiomute = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOMUTE);
    public static final int _mediaselect = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_MEDIASELECT);
    public static final int _www = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_WWW);
    public static final int _mail = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_MAIL);
    public static final int _calculator = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CALCULATOR);
    public static final int _computer = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_COMPUTER);
    public static final int _ac_search = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_SEARCH);
    public static final int _ac_home = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_HOME);
    public static final int _ac_back = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_BACK);
    public static final int _ac_forward = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_FORWARD);
    public static final int _ac_stop = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_STOP);
    public static final int _ac_refresh = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_REFRESH);
    public static final int _ac_bookmarks = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AC_BOOKMARKS);
    
    public static final int _brightnessdown = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_BRIGHTNESSDOWN);
    public static final int _brightnessup = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_BRIGHTNESSUP);
    public static final int _displayswitch = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_DISPLAYSWITCH);
    public static final int _kbdillumtoggle = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KBDILLUMTOGGLE);
    public static final int _kbdillumdown = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KBDILLUMDOWN);
    public static final int _kbdillumup = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_KBDILLUMUP);
    public static final int _eject = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_EJECT);
    public static final int _sleep = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SLEEP);
    public static final int _app1 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_APP1);
    public static final int _app2 = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_APP2);
    
    public static final int _audiorewind = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOREWIND);
    public static final int _audiofastforward = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_AUDIOFASTFORWARD);
    
    public static final int _softleft = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SOFTLEFT);
    public static final int _softright = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_SOFTRIGHT);
    public static final int _call = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_CALL);
    public static final int _endcall = SDL_SCANCODE_TO_KEYCODE(SDL_SCANCODE_ENDCALL);
}
