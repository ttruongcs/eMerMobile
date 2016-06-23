package com.banvien.fcv.mobile.dto;

import java.io.File;

import okhttp3.MediaType;

public class TypeFile {
    private final MediaType mediaType;
    private final File file;
    private final String fileName;

    public TypeFile(MediaType mediaType, File file) {
        this(mediaType, file, file != null ? file.getName() : null);
    }

    public TypeFile(MediaType mediaType, File file, String fileName) {
        this.mediaType = mediaType;
        this.file = file;
        this.fileName = fileName;
    }

    public MediaType mediaType() {
        return mediaType;
    }

    public File file() {
        return file;
    }

    public String fileName() {
        return fileName;
    }
}