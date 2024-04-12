package sorenrahimi.progettog5s2m2.controllers;

import sorenrahimi.progettog5s2m2.entities.Dipendente;
import sorenrahimi.progettog5s2m2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sorenrahimi.progettog5s2m2.payloads.dipendenti.NewDipendenteDTO;
import sorenrahimi.progettog5s2m2.payloads.dipendenti.NewDipendenteResponseDTO;
import sorenrahimi.progettog5s2m2.services.DipendentiService;

import java.io.IOException;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    DipendentiService dipendentiService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteResponseDTO saveAuthor(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation) throws Exception{
        if (validation.hasErrors()) {
            throw new BadRequestException (validation.getAllErrors());
        }
        Dipendente newDipendente = dipendentiService.save(body);
        return new NewDipendenteResponseDTO(newDipendente.getId());
    }

    @GetMapping
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return dipendentiService.getDipendenti(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable int dipendenteId){
        return dipendentiService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    private Dipendente findUserByIdAndUpdate(@PathVariable int dipendenteId, @RequestBody Dipendente body){
        return dipendentiService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findByIdAndDelete(@PathVariable int dipendenteId){
        dipendentiService.findByIdAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/immagine")
    public Dipendente uploadImmagine(@RequestParam("immagine") MultipartFile file,
                               @PathVariable int dipendenteId) {
        try {
            return dipendentiService.uploadImmagine(dipendenteId, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}


