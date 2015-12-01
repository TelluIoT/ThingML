private SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyy 'at' HH:mm:ss.SSS");

private JTabbedPane tabbedPane=new JTabbedPane();
private JFrame frame;

        $MESSAGE_TO_SEND_DECL$

private JTextPane screen;
private JButton clearButton;
private StyledDocument doc;
private final Color alertColor = new Color(255,64,32);
private boolean colorOutput = false;
private JCheckBox showColor;

private synchronized boolean isColorOutput() {
        return colorOutput;
}

private synchronized void setColorOutput(boolean value) {
        this.colorOutput = value;
}

public $NAME$Mock(String name){
        super(name);
        $PORT_DECL$
        initGUI(name);
        }

@Override
public void stop(){
        super.stop();
        frame.setVisible(false);
        }

@Override
public void start(){
        super.start();
        frame.setVisible(true);
        }

@Override
public Component buildBehavior(){
        behavior=new CompositeState("$NAME$",Collections.EMPTY_LIST,new AtomicState("dummy"),Collections.EMPTY_LIST){
@Override
public boolean dispatch(Event event,Port port){
        if(port!=null){
        print(event.getType().getName()+"_via_"+port.getName(),dateFormat.format(new Date())+": " + port.getName() + "?" +event.toString());
        }
        return false;
        }
        };
        return this;
        }

        $MESSAGE_TO_SEND_BEHAVIOR$

public void print(String id,String data){
        try{
        if (isColorOutput())
            doc.insertString(doc.getLength(), formatForPrint(data), doc.getStyle(id));
        else
            doc.insertString(doc.getLength(), formatForPrint(data), null);
        screen.setCaretPosition(doc.getLength());
        }catch(BadLocationException ex){
        ex.printStackTrace();
        }
        }

        $LISTENERS$

private void initGUI(String name){

        GridBagConstraints c=new GridBagConstraints();
        c.gridwidth=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(0,3,0,3);
        c.weightx = 0.5;
        c.weighty = 0;



        c.gridy=0;
        c.gridx=0;
        frame=new JFrame(name);
        frame.setLayout(new GridBagLayout());
        frame.add(tabbedPane,c);

        $MESSAGE_TO_SEND_INIT$

        c.gridy=1;
        c.gridx=0;
        c.gridwidth=1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        frame.add(createJTextPane(),c);

        c.gridy=2;
        c.weighty = 0;
        clearButton=new JButton("Clear Console");
        frame.add(clearButton,c);

        c.gridy = 3;
        c.gridx = 0;
        showColor = new JCheckBox("Colored logs");
        showColor.addItemListener(this);
        frame.add(showColor, c);

        frame.pack();
        clearButton.addActionListener(this);
        addListener(this);
        frame.setVisible(true);
        }

public static JLabel createLabel(String name){
        return new JLabel(name);
        }

public static JButton createSendButton(String name){
        return new JButton("send");
        }

public JScrollPane createJTextPane(){
        screen=new JTextPane();
        screen.setFocusable(false);
        screen.setEditable(false);
        screen.setAutoscrolls(true);

        JScrollPane editorScrollPane=new JScrollPane(screen);
        editorScrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //editorScrollPane.setPreferredSize(new Dimension(480,240));
        //editorScrollPane.setMinimumSize(new Dimension(320,160));

        doc=screen.getStyledDocument();
        //Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);\n"

        $MESSAGE_TO_RECEIVE_BEHAVIOR$

        return editorScrollPane;
        }

private String formatForPrint(String text){
        return(text.endsWith("\n")?text:text+"\n");
        }

@Override
public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==clearButton){
        screen.setText("");
        }
        $ON_ACTION$
        }

@Override
public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == showColor) {
            setColorOutput(!isColorOutput());
        }
}