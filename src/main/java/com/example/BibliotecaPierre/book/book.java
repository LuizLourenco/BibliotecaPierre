package com.example.BibliotecaPierre.book;

import com.example.BibliotecaPierre.author.Author;
import com.example.BibliotecaPierre.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Author authorMain;

    private Author authorSecundary;

    private Integer publicationYear;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.DISPONÍVEL;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum BookStatus{
        DISPONÍVEL, EMPRESTADO, RESERVADO, MANUTENÇÃO;
    }

    public boolean isAvailable(){
        return status == BookStatus.DISPONÍVEL;
    }

    public void markAsBorrowed() throws BusinessException {
        if (!isAvailable()){
            throw new BusinessException("O livro não está disponível para empréstimo");
        }
        this.status = BookStatus.EMPRESTADO;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsAvailable(){
        this.status = BookStatus.DISPONÍVEL;
        this.updatedAt = LocalDateTime.now();
    }
}
