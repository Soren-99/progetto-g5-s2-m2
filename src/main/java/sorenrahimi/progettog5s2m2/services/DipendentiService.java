package sorenrahimi.progettog5s2m2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sorenrahimi.progettog5s2m2.entities.Dipendente;
import sorenrahimi.progettog5s2m2.exceptions.BadRequestException;
import sorenrahimi.progettog5s2m2.exceptions.NotFoundException;
import sorenrahimi.progettog5s2m2.payloads.dipendenti.NewDipendenteDTO;
import sorenrahimi.progettog5s2m2.repositories.DipendentiRepository;

import java.io.IOException;

@Service
public class DipendentiService {
    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private DipendentiRepository dipendentiRepository;

    public Dipendente save(NewDipendenteDTO body) throws IOException {
        dipendentiRepository.findByEmail(body.email()).ifPresent(dipendente -> {

            throw new BadRequestException("L'email " +
                    body.email() + "è gia stata utilizzata");
        });
        Dipendente newDipendente = new Dipendente();
        newDipendente.setImmagine("https://ui-immagini.com/api/?name" +
               body.username() + "+" + body.nome() + "+" + body.cognome());
        newDipendente.setUsername(body.username());
        newDipendente.setNome(body.nome());
        newDipendente.setEmail(body.email());
        newDipendente.setCognome(body.cognome());
        return dipendentiRepository.save(newDipendente);
    }


    public Page<Dipendente> getDipendenti(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return dipendentiRepository.findAll(pageable);
    }

    public Dipendente findById ( int id){
        return dipendentiRepository.findById(id).orElseThrow(() -> new
                NotFoundException(id));
    }

    public void findByIdAndDelete ( int id){
        Dipendente found = this.findById(id);
        dipendentiRepository.delete(found);
    }
    public Dipendente findByIdAndUpdate (int id, Dipendente body){
        Dipendente found = this.findById(id);
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setImmagine(body.getImmagine());
        return dipendentiRepository.save(found);
    }

    public Dipendente uploadImmagine(int id, MultipartFile file) throws
            IOException{
        Dipendente found = this.findById(id);
        String immagineURL = (String)
                cloudinaryUploader.uploader().upload(file.getBytes(),
                        ObjectUtils.emptyMap()).get("url");
        found.setImmagine(immagineURL);
        return dipendentiRepository.save(found);


    }

}
