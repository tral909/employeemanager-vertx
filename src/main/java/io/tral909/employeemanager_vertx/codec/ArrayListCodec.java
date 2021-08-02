package io.tral909.employeemanager_vertx.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.util.ArrayList;

public class ArrayListCodec implements MessageCodec<ArrayList, ArrayList> {
    @Override
    public void encodeToWire(Buffer buffer, ArrayList arrayList) {

    }

    @Override
    public ArrayList decodeFromWire(int pos, Buffer buffer) {
        return null;
    }

    @Override
    public ArrayList transform(ArrayList arrayList) {
        return arrayList;
    }

    @Override
    public String name() {
        return "ArrayListCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}