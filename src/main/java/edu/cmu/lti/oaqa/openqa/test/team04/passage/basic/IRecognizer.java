package edu.cmu.lti.oaqa.openqa.test.team04.passage.basic;

import java.util.List;

public interface IRecognizer {

	  /**
	   * Rule initialization code goes here.
	   * @throws Exception
	   */
	  public void init() throws Exception;
	  
	  /**
	   * Runs through the list of input tokens, classifying as many tokens as
	   * it can into this particular entity.
	   * @param tokens the List of Tokens.
	   * @return the output List of Tokens. The size of the input and output
	   * may not match, since some tokens may be coalesced into a single one
	   * or a token may be broken up into multiple tokens.
	   */
	  public List<Token> recognize(List<Token> tokens);
}
