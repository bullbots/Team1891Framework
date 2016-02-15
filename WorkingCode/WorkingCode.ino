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
        
        break;
      //auto driving state
      case 1:
        
        break;
      //auto shooting state
      case 2:
      
        break;
      //finished auto state
      case 3:
        
        break;
      //teleop driving state
      case 4:
        
        break;
      //collecting state
      case 5:
        
        break;
      //shooting state
      case 6:
        
        break;
      //lifting state
      case 7:
        
        break;
      //end game disabled state
      case 8:
      
        break;
    }
  }
  //red case
  else if (team == true) {
    switch(data[0]) {
      //default sequence, used for before match
      case 0:
        
        break;
      //auto driving state
      case 1:
        
        break;
      //auto shooting state
      case 2:
      
        break;
      //finished auto state
      case 3:
        
        break;
      //teleop driving state
      case 4:
        
        break;
      //collecting state
      case 5:
        
        break;
      //shooting state
      case 6:
        
        break;
      //lifting state
      case 7:
        
        break;
      //end game disabled state
      case 8:
      
        break;
    }
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
