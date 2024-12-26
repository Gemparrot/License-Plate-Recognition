package com.csis231.api.controller;

import com.csis231.api.exception.ResourceNotFoundException;
import com.csis231.api.model.Document;
import com.csis231.api.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Create a new document
    @PostMapping("")
    public ResponseEntity<Document> createDocument(@Valid @RequestBody Document document) {
        Document newDocument = documentService.createDocument(document);
        return ResponseEntity.ok(newDocument);
    }

    // Retrieve a list of documents for a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Document>> getDocumentsForUser(@PathVariable Long userId) {
        List<Document> documents = documentService.getDocumentsForUser(userId);
        return ResponseEntity.ok(documents);
    }

    // Update the license plate for a specific document
    @PutMapping("/{documentId}/licenseplate")
    public ResponseEntity<Document> updateLicensePlate(@PathVariable Long documentId,
                                                       @RequestParam String licensePlate) {
        Document updatedDocument = documentService.updateLicensePlate(documentId, licensePlate);
        return ResponseEntity.ok(updatedDocument);
    }

    // Update the user for a specific document
    @PutMapping("/{documentId}/user")
    public ResponseEntity<Document> updateUser(@PathVariable Long documentId,
                                               @RequestParam Long userId) {
        Document updatedDocument = documentService.updateUser(documentId, userId);
        return ResponseEntity.ok(updatedDocument);
    }

    // Update the CCTV for a specific document
    @PutMapping("/{documentId}/cctv")
    public ResponseEntity<Document> updateCCTV(@PathVariable Long documentId,
                                               @RequestParam Long cctvId) {
        Document updatedDocument = documentService.updateCCTV(documentId, cctvId);
        return ResponseEntity.ok(updatedDocument);
    }

    // Delete a document
    @DeleteMapping("/{documentId}")
    public ResponseEntity deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
