let request = require('request');
let cheerio = require('cheerio');
let mysql = require('mysql');
let async = require('async');
let connection = mysql.createConnection({
  host     : '',
  user     : '',
  password : '',
  port     : '',
  database : '',
});

// 선거 코드 조회 //
function voteCodeApi(){
    const $url = 'http://apis.data.go.kr/9760000/CommonCodeService/getCommonSgCodeList';
    const $serviceKey = '';
    const $pageNo = '1';
    const $numOfRows = '300';
    const $api_url = $url + '?serviceKey=' +$serviceKey +'&pageNo='+ $pageNo  + '&numOfRows=' + $numOfRows;

    request($api_url, function(err,res,body){
        if(err){
            console.log('err:'+err);
        }
        else{
            $ = cheerio.load(body);
            $('item').each(function(idx){
                let num = $(this).find('num').text();
                let sgId = $(this).find('sgId').text();
                let sgName = $(this).find('sgName').text();
                let sgTypecode = $(this).find('sgTypecode').text();
                let sgVotedate = $(this).find('sgVotedate').text();
                console.log(`${num}, ${sgId},${sgName},${sgTypecode},${sgVotedate}`);
                let params = [sgId,sgName,sgTypecode,sgVotedate];
                const sql= 'INSERT INTO votecode(sgId, sgName,sgTypecode, sgVotedate) VALUES(?,?,?,?)';
                connection.query(sql,params);
            })
        }
    })
}


function candidateInfoApi(){
    const $url = 'http://apis.data.go.kr/9760000/PofelcddInfoInqireService/getPofelcddRegistSttusInfoInqire';
    const $serviceKey = '';
    const $pageNo = '1';
    const $numOfRows = '350';
    const $sgId = '20200415';
    const $sgTypecode='7';
    const $api_url = $url + '?serviceKey=' +$serviceKey +'&pageNo='+ $pageNo  + '&numOfRows=' + $numOfRows+'&sgId='+$sgId+'&sgTypecode='+$sgTypecode;
    console.log($api_url);
    request($api_url, function(err,res,body){
        if(err){
            console.log('err:'+err);
        }
        else{
            $ = cheerio.load(body);
            $('item').each(function(idx){
                let num = $(this).find('num').text();
                let sgId = $(this).find('sgId').text();
                let sgTypecode = $(this).find('sgTypecode').text();
                let huboid = $(this).find('huboid').text();
                let sggName = $(this).find('sggName').text();
                let sdName = $(this).find('sdName').text();
                let wiwName = $(this).find('wiwName').text();
                let giho = $(this).find('giho').text();
                let jdName = $(this).find('jdName').text();
                let name = $(this).find('name').text();
                let hanjaName = $(this).find('hanjaName').text();
                let gender = $(this).find('gender').text();
                let birthday = $(this).find('birthday').text();
                let age = $(this).find('age').text();
                let addr = $(this).find('addr').text();
                let jobId = $(this).find('jobId').text();
                let job = $(this).find('job').text();
                let eduId = $(this).find('eduId').text();
                let edu = $(this).find('edu').text();
                let career1 = $(this).find('career1').text();
                let career2 = $(this).find('career2').text();
                let status = $(this).find('status').text();
                console.log(`${num}, ${sgId},${sgTypecode},${huboid},${sggName},${sdName},${wiwName},${giho},${jdName},${name},${hanjaName},${gender},${birthday},${age},${addr},${jobId},${job},${eduId},${edu},${career1},${career2},${status}`);
                let params = [sgId,sgTypecode,huboid,sggName,sdName,wiwName,giho,jdName,name,hanjaName,gender,birthday,age,addr,jobId,job,eduId,edu,career1,career2,status];
                const sql= 'INSERT INTO candidateinfo(sgId,sgTypecode,huboid,sggName,sdName,wiwName,giho,jdName,name,hanjaName,gender,birthday,age,addr,jobId,job,eduId,edu,career1,career2,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)';
                connection.query(sql,params);
            })
        }
    })
}

//직업 코드 조회
function jobCodeApi(){
    console.log("a")
    const $url = 'http://apis.data.go.kr/9760000/CommonCodeService/getCommonJobCodeList';
    const $serviceKey = '';
    const $pageNo = '1';
    const $numOfRows = '10';
    const $sgId = '20200415';
    const $api_url = $url + '?serviceKey=' +$serviceKey +'&pageNo='+ $pageNo  + '&numOfRows=' + $numOfRows+'&sgId=' +$sgId;
    request($api_url,function(err,res,body){
        if(err){
            console.log('err:'+err);
        }
        else{
            $ = cheerio.load(body);
            $('item').each(function(idx){
                let num = $(this).find('num').text();
                let sgId = $(this).find('sgId').text();
                let jobId = $(this).find('jobId').text();
                let jobName = $(this).find('jobName').text();
                console.log(`${num}, ${sgId},${jobId},${jobName}`);
                //let params = [jobId,sgId,jobName];
                //const sql= 'INSERT INTO jobcode(jobId,sgId,jobName) VALUES(?,?,?)';
                //connection.query(sql,params);
            });
        }
    })
}

//선거공약정보조회
function votePromiseApi(){
    const $url = 'http://apis.data.go.kr/9760000/ElecPrmsInfoInqireService/getCnddtElecPrmsInfoInqire';
    const $serviceKey = '';
    const $pageNo = '1';
    const $numOfRows = '3000';
    const $sgId = '20200415';
    const $api_url = $url + '?serviceKey=' +$serviceKey +'&pageNo='+ $pageNo  + '&numOfRows=' + $numOfRows+'&sgId=' +$sgId;
    request($api_url,function(err,res,body){
        if(err){
            console.log('err:'+err);
        }
        else{
            $ = cheerio.load(body);
            $('item').each(function(idx){
                let num = $(this).find('num').text();
                let sgId = $(this).find('sgId').text();
                let jobId = $(this).find('jobId').text();
                let jobName = $(this).find('jobName').text();
                console.log(`${num}, ${sgId},${jobId},${jobName}`);
                let params = [jobId,sgId,jobName];
                const sql= 'INSERT INTO jobcode(jobId,sgId,jobName) VALUES(?,?,?)';
                connection.query(sql,params);
            });
        }
    })
}

function main(){
    connection.connect();
    //voteCodeApi(); // 선거 코드 조회
    candidateInfoApi(); //후보자 정보 조회
    //jobCodeApi(); //직업 코드 조회
}
//main();



function movedata(){
    var folderName = './pdf';
    var fs = require('fs');  

    var listName = fs.readdir(folderName, function(eroor, filelist){
        const listtemp = new Array() 
        var str = ""
        for (let index in filelist){
            str += "'"+filelist[index].substr(0,9)+"'," 
        }
        console.log(str)
        
    })
}

movedata();
