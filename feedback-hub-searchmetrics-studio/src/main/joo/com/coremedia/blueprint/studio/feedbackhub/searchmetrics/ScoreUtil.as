package com.coremedia.blueprint.studio.feedbackhub.searchmetrics {
public class ScoreUtil {

  private static const COLORS:Object = {
    0: '#c71b12',
    5: '#c71b12',
    10: '#cc2710',
    15: '#d3350d',
    20: '#da460a',
    25: '#e25607',
    30: '#e86705',
    35: '#ec7603',
    40: '#ed8503',
    45: '#ed9203',
    50: '#ed9b03',
    55: '#e6a006',
    60: '#daa00a',
    65: '#cca011',
    70: '#bba019',
    75: '#a8a020',
    80: '#95a027',
    85: '#83a02f',
    90: '#73a035',
    95: '#65a03b',
    100: '#5ca03f'
  };

  public static function getColor(x:Number, reverseScoreColor:Boolean = false):String {
    var value:Number = ((x % 5) >= 2.5 ? parseInt('' + (x / 5)) * 5 + 5 : parseInt('' + (x / 5)) * 5);
    if (value > 100) {
      value = 100;
    }

    if (reverseScoreColor) {
      value = 100 - value;
    }

    return COLORS[value];
  }

  public static function formatScore(score:Number):String {
    if(score === undefined) {
      score = 0;
    }
    return parseFloat('' + score).toFixed(0);
  }
}
}
