package com.duoc.sistemainscripcion.resumenes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.duoc.sistemainscripcion.inscripciones.model.Inscripcion;

@Service
public class ResumenServicio {

    public File generarResumen(Inscripcion inscripcion)
            throws IOException {

        // carpeta donde guardar
        File carpeta = new File("resumenes");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        String nombreArchivo = "resumen_" + inscripcion.getId() + ".txt";

        File archivo = new File(carpeta, nombreArchivo);

        try (FileWriter writer = new FileWriter(archivo)) {

            writer.write("===== RESUMEN INSCRIPCION =====\n");
            writer.write("ID: " + inscripcion.getId() + "\n");

            writer.write("Estudiante: "
                    + inscripcion.getNombreEstudiante()
                    + "\n\n");

            writer.write("Cursos inscritos:\n");

            inscripcion.getCursos().forEach(curso -> {
                try {

                    writer.write("- "
                            + curso.getNombre()
                            + " | $"
                            + curso.getCosto()
                            + "\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.write("\nTOTAL A PAGAR: $"
                    + inscripcion.getTotalPagar());
        }

        return archivo;
    }
}