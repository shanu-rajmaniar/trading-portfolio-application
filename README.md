# trading-portfolio-application

# Project api details

- POST /api/v1/stock
- - Fetches the NSE bhavcopy file for previous day, extracts to .csv and populates stock table with relevant details like OHLC prices, etc.
#
- GET /api/v1/stock/{stock_id}
- - Fetches stock details from DB.
#

- POST /api/v1/trade?UserId={}&TradeType={}&Quantity={}&StockId={}
- - Perform stock trade.
#

- GET /api/v1/user_portfolio/{user_id}
- - Fetches user portfolio details like list of holdings, P&L etc.
