package com.kits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kits.model.Document;

public interface FileRepository extends JpaRepository<Document, Integer> {
}
