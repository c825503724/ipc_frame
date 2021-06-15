package anji.ipc.core.at_protocol.type;

public class UnsignedByte implements Comparable<UnsignedByte>{

    private final byte value;

        @Override
    public int compareTo(UnsignedByte o) {
        return Integer.compare(Byte.toUnsignedInt(value), Byte.toUnsignedInt(o.value));
    }

    @Override
    public String toString() {
        return Integer.toString(Byte.toUnsignedInt(value));
    }

    public UnsignedByte(byte value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsignedByte that = (UnsignedByte) o;
        return value == that.value;
    }

}
