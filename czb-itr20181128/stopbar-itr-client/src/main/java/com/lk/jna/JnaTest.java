package com.lk.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author linkai
 * @since 16/8/14
 */
public class JnaTest {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        void printf(String format, Object... args);
    }

    public interface ParkDll extends Library {
        ParkDll INSTANCE = (ParkDll) Native.loadLibrary("TBEParkingDLL", ParkDll.class);

        String TB_GetAmount(String format);
    }

    public static void main(String[] args) {
        CLibrary.INSTANCE.printf("Hello, World\n");
        String str = ParkDll.INSTANCE.TB_GetAmount("{\"interfacetype\":\"1\", \"platenumber\":\"鄂H32C36\"}");
        System.out.println(str);
    }
}
