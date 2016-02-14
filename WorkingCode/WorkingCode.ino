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


void loop() {
  
  //0 in data[] slot 1 means team color is blue, while anyother number means team color is red.
  if (data[2] == 0) {
    team = false;
  }
  else {
    team = true;
  }
  //blue case
  if (team == false) {
    switch(data[0]) {
      //default sequence, used for before match
      case 0:
        for (int x=0; x<=30; x++) {
          for (int i=0; i<=30; i++) {
            int q = i+x;
            if (q > 30) {
              q = q-30;
            }
            setPixel(i, colorFade(q, 0,0,127,0,60,60));
          }
        delay(50);
        strip.show();
        }
        break;
      //first autonomous state
      case 1:
        for (int x=0; x<=30; x++)
        {
          down(x, strip.Color(0,0,127), 20);
        }
        strip.show();
        for (int x=0; x<=30; x++)
        {
          back(x, strip.Color(0,0,127), 20);
        }
        strip.show();
        break;
      //second autonomous state
      case 2:
        for (int x=0; x<=30; x++) {
          movingDot(x, colorFade(x,0,0,127,0,60,60));
        }
        break;
      //third autonomous state
      case 3:
        for (int x=0; x<=30; x++) {
          movingDot(x, colorFade(x,0,0,127,0,60,60));
        }
        break;
      //fourth autonomous state
      case 4:
        wipeColor(strip.Color(0,0,127));
        for (int i=0; i<=29; i++) {
          double color = colorFade(i,127,0,0,0,0,127);
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(x, color);
            strip.show();
          }
        }
        for (int i=0; i<=29; i++) {
          double color = colorFade(i,127,0,0,0,0,127);
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(29-x, color);
            strip.show();
          }
        }
        break;
      //fifth autonomous state
      case 5:
      
        break;
      //sixth autonomous state
      case 6:
      
        break;
      //seventh autonomous state
      case 7:
      
        break;
      //eighth autonomous state
      case 8:
        for (int i=29; i>=0; i--) {
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(i, rainbow(x));
            strip.setPixelColor(x-1, strip.Color(0,0,0));
            strip.show();
            delay(20);
          }
        }
        for (int i=0; i<=13; i++) {
          for (int x=i; x>=0; x--) {
            strip.setPixelColor(x, rainbow(i));
            strip.setPixelColor(x+1, strip.Color(0,0,0));
            strip.setPixelColor(29-x, rainbow(29-i));
            strip.setPixelColor(28-x, strip.Color(0,0,0));
            strip.show();
            delay(20);
          }
          strip.setPixelColor(0,0);
          strip.setPixelColor(29,0);
        }
        break;
      //collecting teleop state
      case 9:
        for (int x=0; x<=29; x++) {
          strip.setPixelColor(x, strip.Color(127,random(16),random(5)));
          strip.show();
          delay(30);
        }
        for (int x=0; x<=29; x++) {
          strip.setPixelColor(29-x, strip.Color(random(15),random(127),127));
          strip.show();
          delay(30);
        }
        break;
      //shooting teleop state
      case 10:
      
        break;
      //driving teleop state
      case 11:
      
        break;
      //lifting teleop state
      case 12:
        
        break;
      //end of game disabled state
      case 13:
        
        break;
    }
  }
  //red case
  else if (team == true) {
    switch(data[0]) {
      //default sequence, used for before match
      case 0:
        wipeColor(strip.Color(0,0,127));
        for (int i=0; i<=29; i++) {
          double color = colorFade(i,127,0,0,0,0,127);
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(x, color);
            strip.show();
          }
        }
        for (int i=0; i<=29; i++) {
          double color = colorFade(i,127,0,0,0,0,127);
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(29-x, color);
            strip.show();
          }
        }
        break;
      //first autonomous state
      case 1:
        for (int x=0; x<=30; x++)
        {
          down(x, strip.Color(127,0,0),20);
        }
        strip.show();
        for (int x=0; x<=30; x++)
        {
          back(x, strip.Color(127,0,0),20);
        }
        strip.show();
        break;
      //second autonomous state
      case 2:
        for (int x=0; x<=30; x++) {
          movingDot(x, colorFade(x, 127,3,0,70,50,40));
        }
        break;
      //third autonomous state
      case 3:
        for (int x=0; x<=30; x++) {
          movingDot(x, colorFade(x, 127,3,0,70,50,40));
        }
        break;
      //fourth autonomous state
      case 4:
      
        break;
      //fifth autonomous state
      case 5:
      
        break;
      //sixth autonomous state
      case 6:
      
        break;
      //seventh autonomous state
      case 7:
      
        break;
      //eighth autonomous state
      case 8:
        for (int i=29; i>=0; i--) {
          for (int x=0; x<=i; x++) {
            strip.setPixelColor(i, rainbow(x));
            strip.setPixelColor(x-1, strip.Color(0,0,0));
            strip.show();
            delay(20);
          }
        }
        for (int i=0; i<=13; i++) {
          for (int x=i; x>=0; x--) {
            strip.setPixelColor(x, rainbow(i));
            strip.setPixelColor(x+1, strip.Color(0,0,0));
            strip.setPixelColor(29-x, rainbow(29-i));
            strip.setPixelColor(28-x, strip.Color(0,0,0));
            strip.show();
            delay(20);
          }
          strip.setPixelColor(0,0);
          strip.setPixelColor(29,0);
        }
        break;
      //first teleop state
      case 9:
      
        break;
      //second teleop state
      case 10:
      
        break;
      //aiming state
      case 11:
      
        break;
      //lifting teleop state
      case 12:
        
        break;
      //match end state
      case 13:
        
        break;
    }
  }
}

//methods for setting LEDs in an order

void wipeColor(double color) {
  for (int x=0; x<30; x++) {
    strip.setPixelColor(x, color);
  }
}

void down(int x, double color, int Delay) {
    strip.setPixelColor(x, color);
    strip.show();
    delay(Delay);
}

void back(int x, double color, int Delay) {
    strip.setPixelColor(29-x, color);
    strip.setPixelColor(30-x, strip.Color(0,0,0));
    strip.show();
    delay(Delay);
}
/*
Flowing control statement
    for (int i=0; i<=30; i++) {
      int q = i+x;
      if (q > 30) {
        q = q-30;
      }
      setPixel(i, color);
    }
    delay(50);
    strip.show();
*/
void setPixel(int i, double color) {
  strip.setPixelColor(i, color);
}

void movingDot(int x, double color) {
  strip.setPixelColor(x, color);
  strip.setPixelColor(x-1, strip.Color(0,0,0));
  strip.show();
  delay(50);
}

//methods for deciding LED color

double Random() {
  return strip.Color(random(126), random(126), random(126));
}

double RandomSelect(int r1, int g1, int b1, int r2, int g2, int b2) {
  int q = random(2);
  if (q == 0) {
    return strip.Color(r1, g1, b1);
  }
  if (q == 1) {
    return strip.Color(r2, g2, b2);
  }
}

double rainbow(int index) {
  int k;
  if (index <= 15) {
    k = index*8;
    return strip.Color(120-k,k,0);
  }
  if (index <= 20 && index > 15) {
    k = (index-15)*12;
    return strip.Color(0,120-k,k);
  }
  if(index <= 30 && index > 20) {
    k = (index-20)*12;
    return strip.Color(k,0,120-k);
  }
}

double colorFade(int x, int r1, int g1, int b1, int r2, int g2, int b2) {
  int rFade = (r2 - r1)/30;
  int gFade = (g2 - g1)/30;
  int bFade = (b2 - b1)/30;
  return strip.Color(r1 + rFade*x, g1 + gFade*x, b1 + bFade*x);
}

double colorFadeBack(int x, int r1, int g1, int b1, int r2, int g2, int b2) {
  if (x<=14) {
    int rFade = (r2 - r1)/15;
    int gFade = (g2 - g1)/15;
    int bFade = (b2 - b1)/15;
    return strip.Color(r1 + rFade*x, g1 + gFade*x, b1 + bFade*x);
  }
  else if (x<=29) {
    int rFade = (r1 - r2)/15;
    int gFade = (g1 - g2)/15;
    int bFade = (b1 - b2)/15;
    return strip.Color(r2 + rFade*(x-14), g2 + gFade*(x-14), b2 + bFade*(x-14));
  }
}

double alternateTwo(int x, int r1, int g1, int b1, int r2, int g2, int b2) {
  int c = x%2;
  if (c == 0) {
    return strip.Color(r1,g1,b1);
  }
  else {
    return strip.Color(r2,g2,b2);
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
