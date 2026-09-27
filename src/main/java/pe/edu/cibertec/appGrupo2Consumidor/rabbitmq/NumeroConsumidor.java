package pe.edu.cibertec.appGrupo2Consumidor.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.appGrupo2Consumidor.config.RabbitMqConfig;
import pe.edu.cibertec.appGrupo2Consumidor.service.MergeSortService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class NumeroConsumidor {

    private final MergeSortService mergeSortService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE)
    public void procesarNumerosMergeSort(String cadenaNumeros) throws InterruptedException {
        log.info("Recibiendo cadena de números del Productor...");
        log.info("Cadena recibida: {}", cadenaNumeros);

        Integer[] integerArray = Stream.of(cadenaNumeros.split(";"))
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        Integer[] arrayOrdenado = mergeSortService.sort(integerArray);

        Thread.sleep(20000);

        log.info("Lista ordenada con MergeSort, fecha y hora {}", LocalDateTime.now());
        log.info("Resultado: {}", Arrays.toString(arrayOrdenado));
        log.info("-----------------------------------------");
    }
}