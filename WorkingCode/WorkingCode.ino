#include <Wire.h>
#include "LPD8806.h"
#include "SPI.h" // Comment out this line if using Trinket or Gemma
#ifdef __AVR_ATtiny85__
 #include <avr/power.h>
#endif

// Example to control LPD8806-based RGB LED Modules in a strip

/*****************************************************************************/

// Number of RGB LEDs in strand:
int nLEDs = 30;

// Chose 2 pins for output; can be any valid output pins:
int dataPin  = 6;
int clockPin = 7;

int data[3];
int j;
boolean team;

// First parameter is the number of LEDs in the strand.  The LED strips
// are 32 LEDs per meter but you can extend or cut the strip.  Next two
// parameters are SPI data and clock pins:
LPD8806 strip = LPD8806(nLEDs, dataPin, clockPin);

// You can optionally use hardware SPI for faster writes, just leave out
// the data and clock pin parameters.  But this does limit use to very
// specific pins on the Arduino.  For "classic" Arduinos (Uno, Duemilanove,
// etc.), data = pin 11, clock = pin 13.  For Arduino Mega, data = pin 51,
// clock = pin 52.  For 32u4 Breakout Board+ and Teensy, data = pin B2,
// clock = pin B1.  For Leonardo, this can ONLY be done on the ICSP pins.
//LPD8806 strip = LPD8806(nLEDs);

void setup() {
#if defined(__AVR_ATtiny85__) && (F_CPU == 16000000L)
  clock_prescale_set(clock_div_1); // Enable 16 MHz on Trinket
#endif

  // Start up the LED strip
  strip.begin();

  // Update the strip, to start they are all 'off'
  strip.show();
  
  Wire.begin(4);
  Wire.onReceive(receiveEvent);
}
double maxTime = 0;


void loop() {
  
  //0 in data[] slot 1 means team color is blue, while anyother number means team color is red.
  if (data[2] == 0) {
    team = false;
  }
  else {
    team = true;
  }
  
  switch(data[0]) {
    //default sequence, used for before match
    case 0:
      if (!team)
      {
        waveBlue();
      }
      else if (team)
      {
        waveRed();
      }
      break;
    //shooting state
    case 1:
      if (!team) launchBlue();
      else launchRed();
      break;
    //driving state
    case 2:
      if(!team) driveSpeedBlue(data[1]);
      else driveSpeedRed(data[1]);
      break;
      
    //collecting state
    case 3:
      if (!team) downAndBackBlue();

      else downAndBackRed();
      break;
    //lifting state
    case 4:
      rainbowCrash();
      break;
    //auto finished state
    case 5:
      if (!team) countdownBlue(data[1]);
      else countdownRed(data[1]);
      break;
    //end game disabled state
    case 6:
      rainbowTetris();
      break;
    case 7:
      lowBattery();
      break;
  }
  for(int a=0; a<30; a++){
    strip.setPixelColor(a,0); 
  }
}

//methods

void countdownRed(double s){
  for(int a=0; a<30; a++){
    strip.setPixelColor(a,strip.Color(120,15,7)); 
  }
  if(s>maxTime)maxTime = s;
  double ratio = (s/maxTime)*30;
  for(int a=0; a<ratio; a++){
   strip.setPixelColor(29-a,0);
  }
  strip.show();
}

void countdownBlue(double s){
  for(int a=0; a<30; a++){
    strip.setPixelColor(a,strip.Color(0,50,120)); 
  }
  if(s>maxTime)maxTime = s;
  double ratio = (s/maxTime)*30;
  for(int a=0; a<ratio; a++){
   strip.setPixelColor(29-a,0);
  }
  strip.show();
  if(ratio <.01)maxTime =0;
}

void driveSpeedRed(double s){
  double b = abs(s)*30;
  if(s>0){
     for(int a = 0; a<b; a++){
       strip.setPixelColor(a,strip.Color(120, 0, 60-b*2));
     }
     for(int a = b-1; a<b; a++){
       strip.setPixelColor(a,strip.Color(80, 0, 120));
     }
  }
  if(s<0){
     for(int a = 0; a<b; a++){
       strip.setPixelColor(a,strip.Color(120, 30-b, 0));
     }
     for(int a = b-1; a<b; a++){
       strip.setPixelColor(a,strip.Color(120, 60, 0));
     }
  }
  strip.show();
}

void driveSpeedBlue(double s){
  double b = abs(s)*30;
  if(s>0){
     for(int a = 0; a<b; a++){
       strip.setPixelColor(a,strip.Color(60-b*2, 0, 120));
     }
     for(int a = b-1; a<b; a++){
       strip.setPixelColor(a,strip.Color(120, 0, 80));
     }
  }
  if(s<0){
     for(int a = 0; a<b; a++){
       strip.setPixelColor(a,strip.Color(0, 90-b*3, 120));
     }
     for(int a = b-1; a<b; a++){
       strip.setPixelColor(a,strip.Color(0, 120, 80));
     }
  }
  strip.show();
}

void launchBlue() {
  for (int x=0; x<=30; x++)
  {
    strip.setPixelColor(x, strip.Color(5,5,127));
  }
  strip.show();
  if (data[1] = 1)
  {
    for (int x=0; x<=40; x++)
    {
      strip.setPixelColor(x, strip.Color(0,80,20));
      strip.setPixelColor(x-10, strip.Color(5,5,127));
      strip.show();
      delay(10);
    }
  }
}

void launchRed() {
  for (int x=0; x<=22; x++)
  {
    strip.setPixelColor(x, strip.Color(127,1,1));
  }
  strip.show();
  if (data[1] = 1)
  {
    for (int x=0; x<=40; x++)
    {
      strip.setPixelColor(x,strip.Color(60,30,0));
      strip.setPixelColor(x-10, strip.Color(127,1,1));
      strip.show();
      delay(10);
    }
  }
}

void waveBlue() {
  for (int x=0; x<=22; x++) {
    for (int i=0; i<=22; i++) {
      int q = i+x;
      if (q > 22) {
        q = q-22;
      }
      strip.setPixelColor(i, colorFade(q, 0,0,127,0,60,60));
    }
  delay(50);
  strip.show();
  }
}

void waveRed() {
  for (int x=0; x<=22; x++) {
    for (int i=0; i<=22; i++) {
      int q = i+x;
      if (q > 22) {
        q = q-22;
      }
      strip.setPixelColor(i, colorFade(q, 127,3,0,70,50,40));
    }
    delay(50);
    strip.show();
  }
}

void downAndBackBlue() {
  for (int x=0; x<=22; x++)
    {
      strip.setPixelColor(x, strip.Color(0,0,127));
      strip.show();
      delay(20);
    }
    for (int x=22; x>=0; x--)
    {
      strip.setPixelColor(x, 0);
      strip.show();
    delay(20);
    }
}

void downAndBackRed() {
  for (int x=0; x<=22; x++)
  {
    strip.setPixelColor(x, strip.Color(127,0,0));
    strip.show();
    delay(20);
  }
  for (int x=22; x>=0; x--)
  {
  strip.setPixelColor(x, 0);
  strip.show();
  delay(20);
  }
}

void rainbowTetris() {
  for (int i=21; i>=0; i--) {
    for (int x=0; x<=i; x++) {
      strip.setPixelColor(x, rainbow(i));
      strip.setPixelColor(x-1, strip.Color(0,0,0));
      strip.show();
      delay(20);
    }
  }
  for (int i=0; i<=12; i++) {
    for (int x=i; x>=0; x--) {
      strip.setPixelColor(x, rainbow(i));
      strip.setPixelColor(x+1, strip.Color(0,0,0));
      strip.setPixelColor(21-x, rainbow(21-i));
      strip.setPixelColor(20-x, strip.Color(0,0,0));
      strip.show();
      delay(20);
    }
    strip.setPixelColor(0,0);
    strip.setPixelColor(21,0);
  }
}

void rainbowCrash() {
  for(int x=0; x<=10; x++)
    {
      strip.setPixelColor(x, strip.Color(80,80,80));
      strip.setPixelColor(x-4, 0);
      strip.setPixelColor(21-x, strip.Color(80,80,80));
      strip.setPixelColor(13-x, 0);
      strip.show();
      delay(50);
    }
    for(int x=0; x<=10; x++)
    {
      strip.setPixelColor(10-x, rainbow(14-x));
      strip.setPixelColor(11+x, rainbow(15+x));
      strip.show();
      delay(20);
    }
}

void lowBattery () {
  for (int x=0; x<22; x++)
  {
    strip.setPixelColor(x, strip.Color(127,127,0));
  }
  strip.show();
  delay(1000);
  for (int x=0; x<22; x++)
  {
    strip.setPixelColor(x, 0);
  }
  strip.show();
  delay(1000);
}

double colorFade(int x, int r1, int g1, int b1, int r2, int g2, int b2) {
  double rFade = (r2 - r1)/22;
  double gFade = (g2 - g1)/22;
  double bFade = (b2 - b1)/22;
  return strip.Color(r1 + rFade*x, g1 + gFade*x, b1 + bFade*x);
}

double rainbow(int index) {
  int k;
  if (index <= 11) {
    k = index*8;
    return strip.Color(120-k,k,0);
  }
  if (index <= 19 && index > 11) {
    k = (index-11)*12;
    return strip.Color(0,120-k,k);
  }
  if(index <= 22 && index > 19) {
    k = (index-19)*12;
    return strip.Color(k,0,120-k);
  }
}

//roborio communication code

void receiveEvent(int howMany)
{
  j = 0;
  while (Wire.available() >= 1)
  {
    data[j] = Wire.read();
    j++;
  }
}
