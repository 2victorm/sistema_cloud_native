package com.duoc.sistemainscripcion.resumenes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Servicio {

        @Autowired
        private S3Client s3Client;

        public String subirArchivo(
                        String bucket,
                        File archivo,
                        Long inscripcionId) {

                String key = "resumenes/"
                                + inscripcionId + "/"
                                + archivo.getName();

                System.out.println("KEY GENERADA: " + key);

                PutObjectRequest request = PutObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .contentType("text/plain")
                                .build();

                s3Client.putObject(
                                request,
                                RequestBody.fromFile(archivo));

                return "Archivo subido correctamente. KEY: " + key;
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

        public InputStream obtenerArchivo(
                        String bucket,
                        String key) {

                GetObjectRequest request = GetObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .build();

                return s3Client.getObject(request);
        }

        public byte[] descargarArchivo(
                        String bucket,
                        String key)
                        throws IOException {

                GetObjectRequest request = GetObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .build();

                return s3Client.getObject(request)
                                .readAllBytes();
        }

        public void subirMultipartFile(
                        String bucket,
                        MultipartFile file)
                        throws IOException {

                PutObjectRequest request = PutObjectRequest.builder()
                                .bucket(bucket)
                                .key(file.getOriginalFilename())
                                .build();

                s3Client.putObject(
                                request,
                                RequestBody.fromBytes(file.getBytes()));
        }

        public void moverArchivo(
                        String bucket,
                        String origen,
                        String destino) {

                CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                                .sourceBucket(bucket)
                                .sourceKey(origen)
                                .destinationBucket(bucket)
                                .destinationKey(destino)
                                .build();

                s3Client.copyObject(copyRequest);

                eliminarArchivo(bucket, origen);
        }

        public void eliminarArchivo(
                        String bucket,
                        String key) {

                DeleteObjectRequest request = DeleteObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .build();

                s3Client.deleteObject(request);
        }
}