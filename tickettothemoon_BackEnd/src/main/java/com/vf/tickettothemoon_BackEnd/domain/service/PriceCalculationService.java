package com.vf.tickettothemoon_BackEnd.domain.service;

import org.springframework.stereotype.Service;
import com.vf.tickettothemoon_BackEnd.domain.model.Price;

@Service
public class PriceCalculationService {
    // In purcentages
    private static double taxes = 5.5;
    private static double discountStudent = 20;
    private static double discountSenior = 10;
    private static double discountChild = 5;

    Price priceht;

    PriceCalculationService(Price priceht) {
        this.priceht = priceht;
    }

    public double calculateTaxes() {
        return getPriceht() - getPriceht() * (1 + taxes / 100);
    }



    public double calculateDiscountStudent() {
        return getPriceht() - (getPriceht() * (1 - discountStudent / 100));
    }

    public double getDiscountSenior() {
        return getPriceht() - (getPriceht() * (1 - discountSenior / 100));
    }

    public double calculateDiscountChild() {
        return getPriceht() - (getPriceht() * (1 - discountChild / 100));
    }

    public static double getTaxes() {
        return taxes;
    }

    public static void setTaxes(double taxes) {
        PriceCalculationService.taxes = taxes;
    }

    public static double getDiscountStudent() {
        return discountStudent;
    }

    public static void setDiscountStudent(double discountStudent) {
        PriceCalculationService.discountStudent = discountStudent;
    }

    public static void setDiscountSenior(double discountSenior) {
        PriceCalculationService.discountSenior = discountSenior;
    }

    public static double getDiscountChild() {
        return discountChild;
    }

    public static void setDiscountChild(double discountChild) {
        PriceCalculationService.discountChild = discountChild;
    }

    private double getPriceht() {
        return priceht.getPriceht();
    }

    public void setPriceht(Price priceht) {
        this.priceht = priceht;
    }



}
