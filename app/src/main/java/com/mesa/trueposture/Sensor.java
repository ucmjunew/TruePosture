package com.mesa.trueposture;

/**
 * Created by June Wu on 4/18/2016.
 */
public class Sensor {
        double resistor1, resistor2, magnet, avgSensor;

        //resistor1
        public void setResistor1(double resistor1) {
            this.resistor1 = resistor1;
        }
        public double getResistor1(){
            return this.resistor1;
        }

        //resistor2
        public void setResistor2(double resistor2){
            this.resistor2 = resistor1;
        }
        public double getResistor2(){
            return this.resistor2;
        }

        //magnet
        public void setMagnet(double magnet){
            this.magnet = magnet;
        }
        public double getMagnet(){
            return this.magnet;
        }

        //magnet
        public void setAverage(double avgSensors){
            this.avgSensor = avgSensor;
        }
        public double getAverage(){
            return this.avgSensor;
        }

}


