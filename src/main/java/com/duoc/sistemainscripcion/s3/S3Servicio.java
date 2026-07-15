package com.duoc.sistemainscripcion.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class S3Servicio {

    @Autowired
    private S3Client s3Client;

    public String subirArchivo(String bucket, File archivo, Long inscripcionId) {
        String key = "inscripciones/" + inscripcionId + "/" + archivo.getName();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("text/plain")
                .build();

        s3Client.putObject(request, RequestBody.fromFile(archivo));

        return "Archivo subido correctamente. KEY: " + key;
    }

    public byte[] descargarArchivo(String bucket, String key) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObject(request).readAllBytes();
    }

    public void eliminarArchivo(String bucket, String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    public List<String> listarArchivos(String bucket) {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(S3Object::key)
                .toList();
    }
}
