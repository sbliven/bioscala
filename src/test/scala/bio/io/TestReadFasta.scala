package bio.io

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import scala.io.Source
import bio._
import java.io._
import bio.IndexedBioSeq

class TestReadFasta extends JUnitSuite {

	val expected = List(IndexedBioSeq("1","ACGT",1),IndexedBioSeq("2","ACGT",2))
	
	def readFromStringAndTest(expected:List[BioSeq],input:String) {
	  val it = Fasta.indexedReader.read(Source.fromString(input))
	  assertEquals(expected,it.toList)		
	}
	
  @Test
  def testReadFastaOk() {
	  readFromStringAndTest(expected,
""">1
ACGT
>2
A
C

G
T""")
  }
  
  @Test
  def testReadFastaWithHeader() {
	  readFromStringAndTest(expected,
	 		  "#Header\n" +
	 		  "#\n" +
	 		  ">1\n" +
	 		  "ACGT\n" + 
	 		  ">2\n" +
	 		  "ACGT")
  }
  
  @Test
  def testReadFastaFileNCBI() {
	  val TempOutput = "ncbi.out.fasta"
	  val tempFile =  new File(TempOutput)
	  tempFile.deleteOnExit()
	  
	  val orig = Fasta.indexedReader.read("data/ncbi.fasta")
	  Fasta.write(TempOutput, orig,70)
	  
	  val seqs1 = Fasta.indexedReader.read("data/ncbi.fasta")
	  val seqs2 = Fasta.indexedReader.read(TempOutput)

    print("seqs1 = " + seqs1)

	  /*for((s1,s2) <- seqs1 zip seqs2) {
	 	  assertEquals(s1,s2)
	  } */
  }
}