package com.jivesoftware.gamification.hbase

import org.apache.hadoop.conf.Configuration

trait HBaseConfigurator {

   def configuration: Configuration

}
