0 - default - used before match - ocean waves and red wave thing - call in disable methods

1 - shooting state - wipe quickly across strip, shooting with ball - call when preparing to shoot

2 - driving state - uses speed for dynamic - as robot speeds up the strip fills up and all lights change color - pass it the rpm as a percentage with1 being 100%

3 - collecting - down and back with red and blue - call when fly wheels are in collecting postion

4 - lifting - two white masses crash together and colors race off strip - call when extending arm

5 - auto finished state - reads match time and shows a progress bar for remaining time - pass it the match time according to the driver station

6 - end game disabled state - tetris stacking rainbow thing - call right at the end of teleop or when robot is disabled

7 - low battery - rapid yellow flashes, must be called by robot when battery is low