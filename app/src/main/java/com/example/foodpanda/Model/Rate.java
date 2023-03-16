package com.example.foodpanda.Model;

import java.util.HashMap;
import java.util.Map;

public class Rate {
    private int numRate;
    private float rate;
    private  int numOneStar;
    private  int numTwoStar;
    private  int numThereStar;
    private  int numFourStar;
    private  int numFiveStar;
  //  private Map<String,RateDetail> rateDetails;

    public Rate() {
        this.rate = 0.0f;
        this.numRate = 0;
        this.numOneStar = 0;
        this.numTwoStar = 0;
        this.numThereStar = 0;
        this.numFourStar = 0;
        this.numFiveStar = 0;

    }

    public Rate(int numRate, float rate, int numOneStar, int numTwoStar, int numThereStar, int numFourStar, int numFiveStar) {
        this.numRate = numRate;
        this.rate = rate;
        this.numOneStar = numOneStar;
        this.numTwoStar = numTwoStar;
        this.numThereStar = numThereStar;
        this.numFourStar = numFourStar;
        this.numFiveStar = numFiveStar;

    }
    public  void  ratting(int ratting){
        switch ((ratting)){
            case 1:
                numOneStar++;
                break;
            case 2:
                numTwoStar++;
                break;
            case 3:
                numThereStar++; break;

            case 4:
                numFourStar++;
                break;
            case 5:
                numFiveStar++;
                break;
        }
        numRate++;
        rate = (numOneStar + numTwoStar*2+ numThereStar*3+ numFourStar*4 +numFiveStar*5)*1.0f/numRate;

    }


    public void  addRate(RateDetail rateDetail){
       // this.rateDetails.put(String.valueOf(rateDetail.getDate()),rateDetail);
    }
    public void abc(int numOneStar, int numTwoStar, int numThereStar, int numFourStar, int numFiveStar){


        this.numOneStar = numOneStar;
        this.numTwoStar = numTwoStar;
        this.numThereStar = numThereStar;
        this.numFourStar = numFourStar;
        this.numFiveStar = numFiveStar;
        numRate = numOneStar+numTwoStar+numThereStar+numFourStar+numFiveStar;
        rate = (numOneStar + numTwoStar*2+ numThereStar*3+ numFourStar*4 +numFiveStar*5)*1.0f/numRate;
     //   this.rateDetails = new HashMap<>();

    }


   /* public Map<String, RateDetail> getRateDetails() {
        return rateDetails;
    }
*/
  /*  public void setRateDetails(Map<String, RateDetail> rateDetails) {
        this.rateDetails = rateDetails;
    }
*/
    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getNumOneStar() {
        return numOneStar;
    }

    public void setNumOneStar(int numOneStar) {
        this.numOneStar = numOneStar;
    }

    public int getNumTwoStar() {
        return numTwoStar;
    }

    public void setNumTwoStar(int numTwoStar) {
        this.numTwoStar = numTwoStar;
    }

    public int getNumThereStar() {
        return numThereStar;
    }

    public void setNumThereStar(int numThereStar) {
        this.numThereStar = numThereStar;
    }

    public int getNumFourStar() {
        return numFourStar;
    }

    public void setNumFourStar(int numFourStar) {
        this.numFourStar = numFourStar;
    }

    public int getNumFiveStar() {
        return numFiveStar;
    }

    public void setNumFiveStar(int numFiveStar) {
        this.numFiveStar = numFiveStar;
    }


}
