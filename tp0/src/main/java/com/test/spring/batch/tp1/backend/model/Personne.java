package com.test.spring.batch.tp1.backend.model;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String civilite;

    public Personne() {
    }

    public Personne(int id, String nom, String prenom, String civillite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civillite;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCivillite() {
        return civilite;
    }

    public void setCivillite(String civillite) {
        this.civilite = civillite;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", civillite='" + civilite + '\'' +
                '}';
    }
}
