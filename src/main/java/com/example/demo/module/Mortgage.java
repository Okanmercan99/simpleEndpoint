package com.example.demo.module;

public class Mortgage {
    private Double amount;
    private Double income;
    private Integer term;
    private String name;
    private String surname;

    public Mortgage(Double amount, Double income, Integer term, String name, String surname) {
        this.amount = amount;
        this.income = income;
        this.term = term;
        this.name = name;
        this.surname = surname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean validateMortgage() {
        return (this.income * 0.3) > (this.amount / this.term);
    }

    @Override
    public boolean equals(Object object) {

        if (this.getClass() != object.getClass())
            return false;
            
        Mortgage other = (Mortgage) object;
        return this.amount.equals(other.getAmount()) && this.term.equals(other.getTerm())
            && this.income.equals(other.getIncome()) && this.surname.equals(other.getSurname()) && this.name.equals(other.getName());
    }
}