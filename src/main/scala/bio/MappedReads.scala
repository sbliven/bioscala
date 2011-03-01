import scala.io.Source

import bio.ReadGraph.readGraph
import bio.ReadFasta.readFasta
import bio.BioSeq
import bio.db.SeqDB
import com.db4o._

package bio {
  object MappedReads {

    def mappedReads(things: Iterator[Thing], seqs: ObjectContainer) = {

      for (thing <- things) {
        if (thing.isInstanceOf[NR]) {
          val nr = thing.asInstanceOf[NR]
          println("Node " + nr.nodeId)
          for (read <- nr.reads) {
            print("\t" + read.readId + " " + read.offsetFromStart + " " + read.startCoord)
            val seq = SeqDB.getSeq(seqs,read.readId)
            println("\t" + Seq)
          }
        } else if (thing.isInstanceOf[SEQ]) {
          val seq = thing.asInstanceOf[SEQ]
        }
      }
    }

    def main(args: Array[String]) {
      if (args.length > 0) {
        val (header, things) = readGraph(Source.fromFile(args(0)))
        
        val dbname="temp.db4o"
        val db = SeqDB.openDB(dbname)
        SeqDB.importFasta(db,Source.fromFile(args(1)));
        mappedReads(things, db);
        db.close        
      }
    }
  }
}