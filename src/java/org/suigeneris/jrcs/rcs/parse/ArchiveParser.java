/* Generated By:JavaCC: Do not edit this line. ArchiveParser.java */
package org.suigeneris.jrcs.rcs.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.suigeneris.jrcs.rcs.Archive;
import org.suigeneris.jrcs.rcs.ArchiveParseAdapter;
import org.suigeneris.jrcs.rcs.Version;
import org.suigeneris.jrcs.rcs.impl.Node;


/**
 * Parses an RCS/CVS style version control archive into an Archive.
 * This class is NOT thread safe.
 * 
 * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
 * @version $Revision: 1.5 $ $Date: 2007-03-15 17:31:32 $
 * @see Archive
 */
public class ArchiveParser implements ArchiveParserConstants {
  static final String ident = "RCS ArchiveParser Parser $version$:";
/*
  public static void main(String args[]) {
    ArchiveParser parser;
    if (args.length == 0)
    {
      System.out.println(ident + "  Reading from standard input . . .");
      parser = new ArchiveParser(System.in);
    }
    else if (args.length == 1)
    {
      System.out.println(ident + "  Reading from file " + args[0] + " . . .");
      try
      {
        parser = new ArchiveParser(new FileReader(args[0]));
      }
      catch (java.io.FileNotFoundException e)
      {
        System.out.println(ident + "  File " + args[0] + " not found.");
        return;
      }
    }
    else
    {
      System.out.println(ident+"  Usage is one of:");
      System.out.println("         java ArchiveParser < inputfile");
      System.out.println("OR");
      System.out.println("         java ArchiveParser inputfile");
      return;
    }
    parser.parse();
  }
*/

  public static void load(Archive arc, CharStream input) throws ParseException
  {
      ArchiveParser parser = new ArchiveParser(input);
      parser.archive(arc);
  }

  public static void load(Archive arc, String fname) throws FileNotFoundException, ParseException
  {
    load(arc, new FastCharStream(new FileReader(fname) ));
  }

  public void parse()
  {
    try
    {
      archive(null);
      System.out.println("RCS ArchiveParser Parser version 1.1:  RCS ArchiveParser parsed successfully.");
    }
    catch (ParseException e)
    {
      System.out.println("RCS ArchiveParser Parser version 1.1:  Encountered errors during parse.");
    }
  }

/**
* PARSER STARTS HERE
*/
  final public void archive(Archive arc) throws ParseException {
   ArchiveParseAdapter adapter = new ArchiveParseAdapter(arc);
    admin(adapter);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUM:
        ;
        break;
      default:
        break label_1;
      }
      delta(adapter);
    }
    desc(arc);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUM:
        ;
        break;
      default:
        break label_2;
      }
      text(adapter);
    }
    jj_consume_token(0);
  }

  final public void admin(ArchiveParseAdapter  arc) throws ParseException {
    head(arc);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BRANCH:
      branch(arc.archive);
      break;
    default:
      ;
    }
    access(arc.archive);
    symbols(arc.archive);
    locks(arc.archive);
    optionals(arc);
  }

  final public void optionals(ArchiveParseAdapter arc) throws ParseException {
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMENT:
      case EXPAND:
      case ID:
        ;
        break;
      default:
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMENT:
        comment(arc.archive);
        break;
      case EXPAND:
        expand(arc.archive);
        break;
      case ID:
        newPhrase(arc.getPhrases());
        break;
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void newPhrases(Map map) throws ParseException {
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        break label_4;
      }
      newPhrase(map);
    }
  }

  final public void head(ArchiveParseAdapter arc) throws ParseException {
    Version v;
    jj_consume_token(HEAD);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUM:
      v = version();
                           arc.setHead(v);
      break;
    default:
      ;
    }
    jj_consume_token(28);
  }

  final public void branch(Archive arc) throws ParseException {
  Version v;
    jj_consume_token(BRANCH);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUM:
      v = version();
                             arc.setBranch(v);
      break;
    default:
      ;
    }
    jj_consume_token(28);
  }

  final public void access(Archive arc) throws ParseException {
    String name;
    jj_consume_token(ACCESS);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        break label_5;
      }
      name = id();
                           arc.addUser(name);
    }
    jj_consume_token(28);
  }

  final public void symbols(Archive arc) throws ParseException {
    String  s;
    Version v;
    jj_consume_token(SYMBOLS);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
      case SYM:
        ;
        break;
      default:
        break label_6;
      }
      s = sym();
      jj_consume_token(29);
      v = version();
                                            arc.addSymbol(s, v);
    }
    jj_consume_token(28);
  }

  final public void locks(Archive arc) throws ParseException {
    String  name;
    Version v;
    jj_consume_token(LOCKS);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        break label_7;
      }
      name = id();
      jj_consume_token(29);
      v = version();
                                            arc.addLock(name, v);
    }
    jj_consume_token(28);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRICT:
      jj_consume_token(STRICT);
      jj_consume_token(28);
               arc.setStrictLocking(true);
      break;
    default:
      ;
    }
  }

  final public void comment(Archive arc) throws ParseException {
  String s;
    jj_consume_token(COMMENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      s = string();
                            arc.setComment(s);
      break;
    default:
      ;
    }
    jj_consume_token(28);
  }

  final public void expand(Archive arc) throws ParseException {
 String s;
    jj_consume_token(EXPAND);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      s = string();
                           arc.setExpand(s);
      break;
    default:
      ;
    }
    jj_consume_token(28);
  }

  final public void newPhrase(Map map) throws ParseException {
  String key;
  String value;
  StringBuffer values = new StringBuffer();
    key = id();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
      case NUM:
      case STRING:
        ;
        break;
      default:
        break label_8;
      }
      value = word();
                     values.append(" " + value);
    }
    jj_consume_token(28);
    if (map != null) map.put(key, values.toString());
  }

  final public String word() throws ParseException {
  String result, right;
    result = simpleWord();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 29:
      right = range();
                                             result = result + right;
      break;
    default:
      ;
    }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public String simpleWord() throws ParseException {
    String  result;
    Version v;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      result = id();
      break;
    case NUM:
      v = version();
                 result = v.toString();
      break;
    case STRING:
      result = string();
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
   {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public String range() throws ParseException {
    String rightSide;
    jj_consume_token(29);
    rightSide = simpleWord();
      {if (true) return ":" + rightSide;}
    throw new Error("Missing return statement in function");
  }

  final public void desc(Archive arc) throws ParseException {
  String s;
    jj_consume_token(DESC);
    s = string();
                          arc.setDesc(s);
  }

  final public void delta(ArchiveParseAdapter arc) throws ParseException {
    Version   v;
    Node      node;
    int[]     d;
    String    s;
    String name = "";
    v = version();
       node = arc.newNode(v);
    jj_consume_token(DATE);
    d = date();
                              node.setDate(d);
    jj_consume_token(28);
    jj_consume_token(AUTHOR);
    name = authorName();
                                      node.setAuthor(name);
    jj_consume_token(28);
    jj_consume_token(STATE);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      s = id();
                            node.setState(s);
      break;
    default:
      ;
    }
    jj_consume_token(28);
    jj_consume_token(BRANCHES);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUM:
        ;
        break;
      default:
        break label_9;
      }
      v = version();
                                 node.addBranch(arc.newBranchNode(v));
    }
    jj_consume_token(28);
    jj_consume_token(NEXT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUM:
      v = version();
                                 node.setRCSNext(arc.newNode(v));
      break;
    default:
      ;
    }
    jj_consume_token(28);
    newPhrases(node.getPhrases());
  }

  final public void text(ArchiveParseAdapter arc) throws ParseException {
  Version v;
  Node node;
  String log;
  String txt;
    v = version();
      node = arc.getNode(v);
    jj_consume_token(LOG);
    log = string();
      node.setLog(log);
    newPhrases(node.getPhrases());
    jj_consume_token(TEXT);
    txt = string();
       node.setText(txt);
  }

  final public String id() throws ParseException {
   Token t;
    t = jj_consume_token(ID);
          {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public String authorId() throws ParseException {
   Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      t = jj_consume_token(ID);
      break;
    case ACCESS:
      /* This is because in RCS keywords and not reserved words, 
      		   so they must be admited wherever an id is expected. In particular,
      		   the RCS format does not impose restrictions on what an author
      		   name is (it probably asumes *nix restrictions), so keywords
      		   and names with spaces must be admited as valid values for 
      		   the author section.
      		   
      		   Example:
      		     author: log; 
      		*/
                      t = jj_consume_token(ACCESS);
      break;
    case AUTHOR:
      t = jj_consume_token(AUTHOR);
      break;
    case BRANCH:
      t = jj_consume_token(BRANCH);
      break;
    case BRANCHES:
      t = jj_consume_token(BRANCHES);
      break;
    case COMMENT:
      t = jj_consume_token(COMMENT);
      break;
    case DATE:
      t = jj_consume_token(DATE);
      break;
    case DESC:
      t = jj_consume_token(DESC);
      break;
    case EXPAND:
      t = jj_consume_token(EXPAND);
      break;
    case HEAD:
      t = jj_consume_token(HEAD);
      break;
    case LOCKS:
      t = jj_consume_token(LOCKS);
      break;
    case LOG:
      t = jj_consume_token(LOG);
      break;
    case NEXT:
      t = jj_consume_token(NEXT);
      break;
    case STATE:
      t = jj_consume_token(STATE);
      break;
    case STRICT:
      t = jj_consume_token(STRICT);
      break;
    case SYMBOLS:
      t = jj_consume_token(SYMBOLS);
      break;
    case TEXT:
      t = jj_consume_token(TEXT);
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public String authorName() throws ParseException {
        String s;
        String name = "";
    label_10:
    while (true) {
      s = authorId();
                             name += " "+ s;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ACCESS:
      case AUTHOR:
      case BRANCH:
      case BRANCHES:
      case COMMENT:
      case DATE:
      case DESC:
      case EXPAND:
      case HEAD:
      case LOCKS:
      case LOG:
      case NEXT:
      case STATE:
      case STRICT:
      case SYMBOLS:
      case TEXT:
      case ID:
        ;
        break;
      default:
        break label_10;
      }
    }
          {if (true) return name.trim();}
    throw new Error("Missing return statement in function");
  }

  final public String sym() throws ParseException {
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SYM:
      t = jj_consume_token(SYM);
      break;
    case ID:
      t = jj_consume_token(ID);
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  final public Version version() throws ParseException {
  Version v;
  int   n;//, r;
    n = num();
    v = new Version(n);
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 30:
        ;
        break;
      default:
        break label_11;
      }
      jj_consume_token(30);
      n = num();
                    v.__addBranch(n);
    }
    {if (true) return v;}
    throw new Error("Missing return statement in function");
  }

  final public int[] date() throws ParseException {
  int[] n = new int[6];
    n[0] = num();
    jj_consume_token(30);
    n[1] = num();
    jj_consume_token(30);
    n[2] = num();
    jj_consume_token(30);
    n[3] = num();
    jj_consume_token(30);
    n[4] = num();
    jj_consume_token(30);
    n[5] = num();
   {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  final public int num() throws ParseException {
              Token t;
    t = jj_consume_token(NUM);
                                      {if (true) return Integer.parseInt(t.image);}
    throw new Error("Missing return statement in function");
  }

  final public String string() throws ParseException {
 Token t;
    t = jj_consume_token(STRING);
    {if (true) return Archive.unquoteString(t.image);}
    //return t.image;

    throw new Error("Missing return statement in function");
  }

  public ArchiveParserTokenManager token_source;
  public Token token, jj_nt;
  private int jj_ntk;

  public ArchiveParser(CharStream stream) {
    token_source = new ArchiveParserTokenManager(stream);
    token = new Token();
    jj_ntk = -1;
  }

  public void ReInit(CharStream stream) {
    token_source.ReInit(stream);
    token = new Token();
    jj_ntk = -1;
  }

  public ArchiveParser(ArchiveParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  public void ReInit(ArchiveParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      return token;
    }
    token = oldToken;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}