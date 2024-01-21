const express = require("express");
const app = express();
const port = 3000;
const path = require('path')


app.use(express.static(path.join('D:\\dulred\\learning\\test\\vite-project', 'dist')))

app.listen (port,()=>{
    console.log(`Example app listening on port ${port}`)
})

