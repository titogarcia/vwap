For this task, the candidate is expected to demonstrate good software design and implementation within the context of low-latency systems.  The solution provided is expected to be of reasonable quality for a prototype.  Wherever the problem is unclear, the candidate should make (and state) any assumptions he or she thinks appropriate within the application context.  Finally, although there is no time limit, this task is expected to take no longer than an hour.

The task:

Design and implement a Java “prototype” application that accomplishes the following:

1. For each of i instruments, the application should subscribe for continuous market depth updates on m simulated market venues.

2. The application should print out VWAP two-way prices for an instrument each time it receives a market depth tick for that instrument (from any market venue) in real-time.

 

 

ACLARACIONES CONCEPTUALES:

Instrumento - un instrumento es "algo" que compramos o vendemos en un mercado, imaginemos el mercado de la esquina, y compramos manzanas, pues nuestro instrumento son manzanas.

 

Market depth tick -> los precios que hay en mercado, se mueven en "ticks" es decir, si las manzanas estan a 1.90 1.95....el tick de las manzanas es de 0.05.  los precios son de compra y venta, en el mercado hay gente que quiere vender manzanas y gente que quiere comprar manzanas.
La gente compra y vende una cantidad a un precios, yo voy al mercado, y quiero comprar 10 manzanas a 1.90 cada manzana, habrá un señor que venda manzanas, por ejemplo 20 manzanas a 1.95. Cuando realmente se efectúa la compra, se hace un cruce o trade (n manzanas a precio x)

 

high, low and close ->
High es el precio más alto en un periodo de tiempo (puede ser todo el día, o en este caso , el timeFrame que tengamos).
Low es el precio más bajo en un periodo de tiempo.
Close es el último precio de un periodo de tiempo.
 

VWAP

Definicion de VWAP -> The volume weighted average price (VWAP) is a trading benchmark used by traders that gives the average price a security has traded at throughout the day, based on both volume and price. It is important because it provides traders with insight into both the trend and value of a security

 

Calculating VWAP

The VWAP calculation is performed by charting software and displays an overlay on the chart representing the calculations. This display takes the form of a line, similar to other moving averages. How that line is calculated is as follows:

•            Choose your time frame (tick chart, 1 minute, 5 minutes, etc.)

•            Calculate the typical price for the first period (and all periods in the day following). Typical price is attained by taking adding the high, low and close, and dividing by three: (H+L+C)/3

•            Multiply this typical price by the volume for that period. This will give you a value called TPV.

•            Keep a running total of the TPV values, called cumulative-TPV. This is attained by continually adding the most recent TPV to the prior values (except for the first period, since there will be no prior value). This figure should get larger as the day progresses.

•            Keep a running total of cumulative volume. Do this by continually adding the most recent volume to the prior volume. This number should also get larger as the day progresses.

•            Calculate VWAP with your information: [cumulative TPV ÷ cumulative volume]. This will provide a volume weighted average price for each period and will provide the data to create the flowing line that overlays the price data on the chart.

 