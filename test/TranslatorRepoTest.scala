import org.scalatest._
import collection.mutable.Stack

/**
 * Created by Seth Kong on 9/26/16.
 * Testing Translator Repository
 */
class TranslatorRepoTest extends FlatSpec with Matchers {
  // Sample codes  Quick Start excerpted from
  // http://www.scalatest.org/quick_start
  // Both FlatSpec and Matchers belong to org.scalatest._ package
  // We are using something should...in
  // In the in {} block, we access the instance of the interested object and
  // inspecting its behaviors by verifying the inputs and outputs of the methods

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a[NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }
}
