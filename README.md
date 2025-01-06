# CryptoChartApp

This application provides a real-time visualization of cryptocurrency prices. It fetches the latest cryptocurrency data from the CoinMarketCap API and displays interactive charts for selected cryptocurrencies. The application also calculates and displays the price change over different time intervals (minute, hour, day, week).

## Features
- Real-time cryptocurrency price tracking.
- Interactive line charts displaying price changes over time.
- Displays price changes for the last minute, hour, day, and week.
- Modern and visually appealing user interface with charts and price change labels.

## Requirements
To use this application, you need to:
1. Register for an account on [CoinMarketCap](https://coinmarketcap.com/api/).
2. Obtain your personal API key from the CoinMarketCap API dashboard.

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/CryptoChartApp.git
   
2. Navigate to the project directory:
   cd CryptoChartApp

3. Open the CryptoAPI.java file and insert your API key in the following line:
   private static final String API_KEY = "<YOUR_API_KEY>";

4. Build the project using Maven:
   mvn clean install
   
5. Run the application:
   mvn exec:java

The application will open a window displaying real-time cryptocurrency charts. You can customize which cryptocurrencies are shown by adjusting the number of displayed charts in the Main.java file.

## How It Works
1. The application uses the CoinMarketCap API to fetch the latest cryptocurrency data in JSON format.
2. The data is parsed, and charts are created using JFreeChart.
3. A Timer is used to update the charts every second with the latest data.
4. The application calculates and displays the price change over various time intervals.

## Dependencies
- Spring Boot: For making API requests.
- Jackson Databind: For parsing JSON responses.
- JFreeChart: For generating and displaying charts.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
https://coinmarketcap.com/api/ for providing real-time cryptocurrency data.
