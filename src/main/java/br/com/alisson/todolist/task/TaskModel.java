package br.com.alisson.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "tb_task")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
  
  /*   @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id") */
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createAt;

    public void setTitle(String title) throws Exception {
      if(title.length() > 50 ){
        throw new Exception("O Campo title deve conter no maximo 50 caracteres.");
      }
      this.title = title;
    }

}
