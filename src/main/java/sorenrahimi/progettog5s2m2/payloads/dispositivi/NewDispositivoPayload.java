package sorenrahimi.progettog5s2m2.payloads.dispositivi;

import jakarta.validation.constraints.NotNull;
import sorenrahimi.progettog5s2m2.entities.StatoDispositivo;

public record NewDispositivoPayload(
        @NotNull(message = "L'id del dipendente è obbligatorio")
        Integer dipendenteId,
        @NotNull(message = "Il tipo è obbligatori0")
        String tipo,
        @NotNull(message = "Lo stato del dispositivo è obbligatori0")
        StatoDispositivo statoDispositivo
) {
}
