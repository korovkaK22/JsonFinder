package com.jsonproject.finder.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class JsonProcessingStats {
   private final AtomicInteger totalObjectsProcessed = new AtomicInteger(0);
   private final AtomicInteger objectsWithMistake = new AtomicInteger(0);
   private final AtomicInteger invalidFiles = new AtomicInteger(0);
   private final AtomicInteger allFilesProcessed = new AtomicInteger(0);


   public void addTotalObjectsProcessed(int amount) {
      totalObjectsProcessed.addAndGet(amount);
   }

   public void addTotalObjectsProcessed() {
      addTotalObjectsProcessed(1);
   }



   public void addObjectsWithMistake(int amount) {
      objectsWithMistake.addAndGet(amount);
   }

   public void addObjectsWithMistake() {
      addObjectsWithMistake(1);
   }



   public void addInvalidFiles(int amount) {
      invalidFiles.addAndGet(amount);
   }

   public void addInvalidFiles() {
      addInvalidFiles(1);
   }


   public void addAllFilesProcessed(int amount) {
      allFilesProcessed.addAndGet(amount);
   }

   public void addAllFilesProcessed() {
      addAllFilesProcessed(1);
   }


   public String getStatisticString() {
      StringBuilder sb = new StringBuilder(String.format("Processed all %d files", allFilesProcessed.get()));
      if (invalidFiles.get() !=0 ){
         sb.append(String.format(", %d files were corrupted", invalidFiles.get()));
      }
      sb.append(String.format(", total processed %d objects", totalObjectsProcessed.get()));
      if (objectsWithMistake.get() !=0 ){
         sb.append(String.format(", %d weren't have exact parameter", objectsWithMistake.get()));
      }
      return sb.toString();
   }

}

