    package com.devsync.model;

    import com.devsync.enums.UserType;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.List;

    @Data
    @Entity
    @Table(name = "users")
    public class User {

        // Getters et Setters
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "prenom" , nullable = false)
        private String prenom;

        @Column(name = "email" ,unique = true, nullable = false)
        private String email;

        @Column(name = "password" , nullable = false)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(name = "usertype" , nullable = false)
        private UserType userType;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Task> tasks;

        public User(long l, String user, String two, String mail, String password, String user2, com.devsync.enums.UserType userType, int i, int i1) {
        }

        public enum UserType {
            USER, MANAGER
        }

        public User() {}

        public User( String name, String prenom, String email, String password, UserType userType) {

            this.name = name;
            this.prenom = prenom;
            this.email = email;
            this.password = password;
            this.userType = userType;
        }


    }


