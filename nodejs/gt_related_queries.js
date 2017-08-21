"use strict";

var googleTrends = require('D:\\Nodejs\\node_modules\\npm\\node_modules\\google-trends-api\\lib\\google-trends-api.min.js');
var util = require('util')
var result = [];

function recursive_key_search(obj)
{
  var key = "";
  for (key in obj)
  {
    if(!isNaN(Number(key)))
    {
      return obj;
    }
    break;
  }
  return recursive_key_search(obj[key]);
};

function related_queries(search_word, start_date, end_date, country)
{
  start_date = new Date(start_date);
  end_date = new Date(end_date);
  googleTrends.relatedQueries({keyword: search_word, startTime: start_date,
                                endTime: end_date, geo: country}, function(err, results){
                                  if(err) console.log("error!", err);
                                  else {
                                    var queries = recursive_key_search(JSON.parse(results));
                                    console.log(JSON.stringify(queries[0]["rankedKeyword"]));
                                    console.log("splitHereForList");
                                    console.log(JSON.stringify(queries[1]["rankedKeyword"]));
                                  }
                                })
}

related_queries(process.argv[2], process.argv[3], process.argv[4], 'KR');
