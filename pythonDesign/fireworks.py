"""Python Sendoff"""

#imports

import turtle
import math
import random

#scripting
#inits
screenWidth=800
screenHeight=640
screen = turtle.screensize(canvwidth=screenWidth, canvheight=screenHeight)
t = turtle.Turtle()
beginningX=-(screenWidth*.5)+1
beginningY=(screenHeight*.5)+1
hypotaneus = math.sqrt(80*80+80*80)

#reusable functions
def jump(target,x,y):
	target.penup()
	target.goto(x,y)
	target.pendown()
def drawT(target):
	target.forward(80)
	target.right(90)
	target.forward(20)
	target.right(90)
	target.forward(30)
	target.left(90)
	target.forward(60)
	target.right(90)
	target.forward(20)
	target.right(90)
	target.forward(60)
	target.left(90)
	target.forward(30)
	target.right(90)
	target.forward(20)
def drawH(target):
	target.forward(80/3)
	target.right(90)
	target.forward(30)
	target.left(90)
	target.forward(80/3)
	target.left(90)
	target.forward(30)
	target.right(90)
	target.forward(80/3)
	target.right(90)
	target.forward(80)
	target.right(90)
	target.forward(30)
	target.right(90)
	target.forward(30)
	target.left(90)
	target.forward(80/3)
	target.left(90)
	target.forward(30)
	target.right(90)
	target.forward(80/3)
	target.right(90)
	target.forward(80)
def drawA(target):
	target.right(45)
	target.forward(hypotaneus+20)#offset for finishing A
	target.right(135)#complete the 90
	target.forward(20)
	target.right(45)
	target.forward(hypotaneus)
	target.left(45)
	target.forward(20)
	target.left(45)
	target.forward(hypotaneus)
	target.right(45)
	target.forward(20)
	target.right(135)
	target.forward(hypotaneus+20)
	target.right(45)
	target.forward(33)
def drawN(target):
	target.forward(10)
	target.right(45)
	target.forward(hypotaneus/2)
	target.left(135)
	target.forward(30)
	target.right(90)
	target.right(10)
	target.right(90)
	target.forward(80)
	target.right(90)
	target.forward(10)
	target.right(45)
	target.forward(hypotaneus/2)
	target.left(90)
	target.forward(30)
	target.right(90)
	target.forward(10)
	target.right(90)
	target.forward(80)
	target.right(90)
	target.right(90)

def neverMind(turtle):
	jump(turtle,0,0)
	while(True):
		turtle.reset()
		turtle.setheading(random.randrange(360))
		x = 0
		rVal = random.randrange(360)
		lVal = random.randrange(360)
		fLong = random.randrange(100,400)
		fShort = random.randrange(200)
		bVal = random.randrange(500)
		turtle.speed(0)
		while(x<50):
			x+=1
			print(x)
			turtle.forward(fLong)
			turtle.right(rVal)
			turtle.forward(fShort)
			turtle.left(lVal)
			turtle.back(bVal)
			jump(turtle,0,0)
			

#drawing
jump(t,beginningX,beginningY)

drawT(t)
t.setheading(0)
jump(t,beginningX+90,beginningY)
drawH(t)
t.setheading(0)
jump(t,beginningX+180,beginningY)
drawA(t)
t.setheading(0)
jump(t,beginningX+270,beginningY)
drawN(t)
t.setheading(0)
#drawK(t)
#drawSpace(t)
#drawY(t)
#drawO(t)
#drawU(t)
neverMind(t)

#housekeeping
input("")#keep window open


def thankYou(target):
	jump(target,beginningX,beginningY)
thankYou(t)
