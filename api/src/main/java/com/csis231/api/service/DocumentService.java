package com.csis231.api.service;

import com.csis231.api.exception.ResourceNotFoundException;
import com.csis231.api.model.CCTV;
import com.csis231.api.model.Document;
import com.csis231.api.repository.CCTVRepository;
import com.csis231.api.repository.DocumentRepository;
import com.csis231.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CCTVRepository cctvRepository;


    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> getDocumentsForUser(Long userId) {
        return documentRepository.findByUserId(userId);
    }

    public Document updateLicensePlate(Long documentId, String licensePlate) {
        Document document = (Document) documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document" + documentId));
        document.setLicensePlate(licensePlate);
        return documentRepository.save(document);
    }

    public Document updateUser(Long documentId, Long userId) {
        Document document = (Document) documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document" + documentId));
        document.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId)));
        return documentRepository.save(document);
    }

    public Document updateCCTV(Long documentId, Long cctvId) {
        Document document = (Document) documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document" + documentId));
        document.setCctv((CCTV) cctvRepository.findById(cctvId)
                .orElseThrow(() -> new ResourceNotFoundException("CCTV" + cctvId)));
        return documentRepository.save(document);
    }

    public void deleteDocument(Long documentId) {
        Document document = (Document)documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document" + documentId));
        documentRepository.delete(document);
    }
}
