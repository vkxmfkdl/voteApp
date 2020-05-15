const express = require('express');
const ejs = require('ejs');
const app = express();
let mysql = require('mysql');
const bodyParser = require('body-parser');

app.set("view engine","ejs");
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());
app.use(express.static(__dirname+'/'));

let connection = mysql.createConnection({
  host     : '',
  user     : '',
  password : '',
  port     : '',
  database : '',
});

app.get('/', (req, res) => {
  res.send('NODE JS')
})

app.get('/pdfView',(req,res)=>{
  let huboidFromParam = req.query.huboid;
  res.render("pdfViewer.ejs",{"number":huboidFromParam});
})

app.get('/detailInfo',(req,res)=>{

  let huboidFromParam = req.query.huboid; // 페이지수
  let params = [huboidFromParam];
  const sql= 'SELECT name'
  +', huboid'
  +', hanjaName'
  +', birthday'
  +', jdName'
  +', sgTypeCode'
  +', sdName'
  +', sggName'
  +', edu'
  +', job'
  +', career1'
  +', career2 '
  +'FROM electinfo '
  +'where huboid = ?';
  connection.query(sql,params,function(err,results,field){
    res.json(results);
  });
})

app.get('/resultView',(req,res)=>{

  let pageFromParam = req.query.page; // 페이지수
  let sdNameFromParam = req.query.sdName; //경기도
  let nameFromParam = req.query.name // 이름
  let genderFromParam = req.query.gender //성별
  let ageStartFromParam = req.query.ageStart //나이
  let ageEndFromParam = req.query.ageEnd //나이
  let jdNameFromParam = req.query.jdName //나이

  if(pageFromParam===undefined){
    pageFromParam=1;
  }

  if(sdNameFromParam===undefined){
    sdNameFromParam="%";
  }else{
    sdNameFromParam+="%";
  }

  if(nameFromParam == undefined){
    nameFromParam ="%";
  }else{
    nameFromParam+="%";
  }
  
  if(genderFromParam == undefined){
    genderFromParam ="%";
  }else{
    genderFromParam+="%";
  }

  if(ageStartFromParam == undefined){
    ageStartFromParam ="30000000";
  }

  if(ageEndFromParam == undefined){
    ageEndFromParam ="10000000";
  }

  if(jdNameFromParam == undefined){
    jdNameFromParam="%";
  }else{
    jdNameFromParam+="%";
  }

  let start = (pageFromParam-1)*10;
  let params = [sdNameFromParam,nameFromParam,genderFromParam,ageStartFromParam,ageEndFromParam, jdNameFromParam, start];
  const sql= 'SELECT huboid'
  +', name '
  +', hanjaName '
  +', birthday '
  +', jdName '
  +', sdName '
  +', sggName '
  +'FROM electinfo '
  +'where sdName like ? '
  +'and name like ? '
  +'and gender like ? '
  +'and birthday <= ? '
  +'and birthday >= ? '
  +'and jdName like ? '
  +'limit ?,10 ';
  connection.query(sql,params,function(err,results,field){
    res.json(results);
  });
})


app.listen(3000, () => {
  console.log(`3000번 port에 http server를 띄웠습니다.`)
})