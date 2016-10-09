import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by nationalist (Seth Kong) on 9/26/16.
 * Testing data access layer using ReactiveMongo module
  * Matchers trait has rich-features for verifying the output of the methods
  * This experts is taken from
  * http://www.scalatest.org/user_guide/using_matchers#checkingEqualityWithMatchers
 */
class ReactiveMongoRepoTest extends FlatSpec with Matchers {

  "A software engineer" should "be able to create something useful" in {
    5 should equal(2 + 3)
    "Ace" shouldEqual ("Ace")
    List(1, 2, 3) should contain oneOf (3, 4, 5)
    Array(1, 2, 3) should contain atLeastOneOf (3, 4, 5)
  }

}
