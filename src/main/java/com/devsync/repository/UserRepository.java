    package com.devsync.repository;

    import com.devsync.model.User;
    import jakarta.persistence.*;

    import java.util.List;
    import java.util.Optional;

    public class UserRepository {
        private EntityManagerFactory emf;

        public UserRepository( ) {
            this.emf = Persistence.createEntityManagerFactory("DevSyncPU");
        }

        public void addUser(User user) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
            } finally {
                em.close();
            }
        }


        public List<User> findAllUsers() {
            EntityManager em = emf.createEntityManager();
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            em.close();
            return users;
        }


        public void updateUser(User user) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(user);  // Use merge for updating
            em.getTransaction().commit();
            em.close();
        }

        public void deleteUser(Long id) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
            em.close();
        }

        public User findById(Long id) {
            EntityManager em = emf.createEntityManager();
            User user = em.find(User.class, id);
            em.close();
            return user;
        }

        public User findByEmail(String email) {
            EntityManager em = emf.createEntityManager();
            try {
                return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("No user found with email: " + email);
                return null;
            } catch (Exception e) {
                e.printStackTrace(); // Log other exceptions
                return null;
            } finally {
                em.close();
            }
        }





    }





