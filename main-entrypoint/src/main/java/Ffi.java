import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class Ffi {
    private static final Linker NATIVE_LINKER = Linker.nativeLinker();
    private static final String LIBRARY_NAME = "dylib/untitled1"; // Without the 'dll' extension
    private static final SymbolLookup STD_LIB = NATIVE_LINKER.defaultLookup();
    private static final Arena ARENA = Arena.global();
    private static final SymbolLookup MY_LIB = SymbolLookup.libraryLookup(LIBRARY_NAME, ARENA);

    public static void main(String[] args) throws Throwable {
        // Define the memory layout for the struct
        GroupLayout myStructLayout = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName("a"),  // i32 in Rust, int in Java
                ValueLayout.JAVA_SHORT, // 16bit padding because struct is aligned to 8 bytes instead of 6
                ValueLayout.JAVA_SHORT.withName("b") // i16 in Rust, short in Java
        );
        // Define the method type matching the native function
        MethodHandle get_struct = NATIVE_LINKER.downcallHandle(
                MY_LIB.find("get_struct").get(),  // Lookup the symbol for the add function
                FunctionDescriptor.of(myStructLayout)
        );

        // Use the method handle to call the add function
        MemorySegment myStructSegment = (MemorySegment) get_struct.invokeExact((SegmentAllocator) ARENA);

        // Continuing from the previous code
        int aValue = myStructSegment.getAtIndex(ValueLayout.JAVA_INT, 0); // index is in times the size of this layout
        short bValue = myStructSegment.getAtIndex(ValueLayout.JAVA_SHORT, 2); // 2 shorts offset
        System.out.println("Struct MyStruct { a: " + aValue + ", b: " + bValue + " }");
    }
}
