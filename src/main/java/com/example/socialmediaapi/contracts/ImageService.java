package com.example.socialmediaapi.contracts;

public interface ImageService {

    byte[] compressBytes(byte[] data);
    byte[] decompressBytes(byte[] data);

}
