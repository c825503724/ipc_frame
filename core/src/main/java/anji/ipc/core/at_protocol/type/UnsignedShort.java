package anji.ipc.core.at_protocol.type;

import lombok.Data;

@Data
public class UnsignedShort implements Comparable<UnsignedShort> {

    private final short value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsignedShort that = (UnsignedShort) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public static UnsignedShort fromLongBits(short bits) {
        return new UnsignedShort(bits);
    }

    @Override
    public int compareTo(UnsignedShort o) {
        return Integer.compare(Short.toUnsignedInt(value), o.getValue());
    }


    @Override
    public String toString() {
        return Integer.toString(Short.toUnsignedInt(value));
    }
}
