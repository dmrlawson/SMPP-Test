/**
 * Original Author:Neil Youngman
 * Released under the GNU General Public License version 2.0 or later.
 */

package uk.org.youngman.smpp.test.gui;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import org.smpp.Data;


/**
 * A panel for displaying/editing PDU headers.
 */
public class PDUHeaderPane extends JPanel
{
    enum CommandID
    {
        GENERIC_NACK( Data.GENERIC_NACK ),
        BIND_RECEIVER( Data.BIND_RECEIVER ),
        BIND_RECEIVER_RESP( Data.BIND_RECEIVER_RESP ),
        BIND_TRANSMITTER( Data.BIND_TRANSMITTER ),
        BIND_TRANSMITTER_RESP( Data.BIND_TRANSMITTER_RESP ),
        QUERY_SM( Data.QUERY_SM ),
        QUERY_SM_RESP( Data.QUERY_SM_RESP ),
        SUBMIT_SM( Data.SUBMIT_SM ),
        SUBMIT_SM_RESP( Data.SUBMIT_SM_RESP ),
        DELIVER_SM( Data.DELIVER_SM ),
        DELIVER_SM_RESP( Data.DELIVER_SM_RESP ),
        UNBIND( Data.UNBIND ),
        UNBIND_RESP( Data.UNBIND_RESP ),
        REPLACE_SM( Data.REPLACE_SM ),
        REPLACE_SM_RESP( Data.REPLACE_SM_RESP ),
        CANCEL_SM( Data.CANCEL_SM ),
        CANCEL_SM_RESP( Data.CANCEL_SM_RESP ),
        BIND_TRANSCEIVER( Data.BIND_TRANSCEIVER ),
        BIND_TRANSCEIVER_RESP( Data.BIND_TRANSCEIVER_RESP ),
        OUTBIND( Data.OUTBIND ),
        ENQUIRE_LINK( Data.ENQUIRE_LINK ),
        ENQUIRE_LINK_RESP( Data.ENQUIRE_LINK_RESP ),
        SUBMIT_MULTI( Data.SUBMIT_MULTI ),
        SUBMIT_MULTI_RESP( Data.SUBMIT_MULTI_RESP ),
        ALERT_NOTIFICATION( Data.ALERT_NOTIFICATION ),
        DATA_SM( Data.DATA_SM ),
        DATA_SM_RESP( Data.DATA_SM_RESP );

        private int id;

        private static Hashtable<Integer,CommandID> reverse =
                new Hashtable<Integer, CommandID>();

        CommandID( int id )
        {
            this.id = id;
        }

        int getId()
        {
            return id;
        }

        static CommandID find( int id )
        {
            return reverse.get( id );
        }

        static void register( CommandID id )
        {
            reverse.put( id.getId(), id );
        }
    }

    /**
     * Java won't let me populate CommandID.reverse from the constructors
     */
    static {
        for( CommandID id: CommandID.values() )
        {
            CommandID.register( id );
        }
    }

    enum CommandStatus {
        ESME_ROK( 0 ),
        ESME_RINVMSGLEN( 1 ),
        ESME_RINVCMDLEN( 2 ),
        ESME_RINVCMDID( 3 ),
        ESME_RINVBNDSTS( 4 ),
        ESME_RALYBND( 5 ),
        ESME_RINVPRTFLG( 6 ),
        ESME_RINVREGDLVFLG( 7 ),
        ESME_RSYSERR( 8 ),
        ESME_RINVSRCADR( 10 ),
        ESME_RINVDSTADR( 11 ),
        ESME_RINVMSGID( 12 ),
        ESME_RBINDFAIL( 13 ),
        ESME_RINVPASWD( 14 ),
        ESME_RINVSYSID( 15 ),
        ESME_RCANCELFAIL( 17 ),
        ESME_RREPLACEFAIL( 19 ),
        ESME_RMSGQFUL( 20 ),
        ESME_RINVSERTYP( 21 ),
        ESME_RADDCUSTFAIL( 25 ),
        ESME_RDELCUSTFAIL( 26 ),
        ESME_RMODCUSTFAIL( 27 ),
        ESME_RENQCUSTFAIL( 28 ),
        ESME_RINVCUSTID( 29 ),
        ESME_RINVCUSTNAME( 31 ),
        ESME_RINVCUSTADR( 33 ),
        ESME_RINVADR( 34 ),
        ESME_RCUSTEXIST( 35 ),
        ESME_RCUSTNOTEXIST( 36 ),
        ESME_RADDDLFAIL( 38 ),
        ESME_RMODDLFAIL( 39 ),
        ESME_RDELDLFAIL( 40 ),
        ESME_RVIEWDLFAIL( 41 ),
        ESME_RLISTDLSFAIL( 48 ),
        ESME_RPARAMRETFAIL( 49 ),
        ESME_RINVPARAM( 50 ),
        ESME_RINVNUMDESTS( 51 ),
        ESME_RINVDLNAME( 52 ),
        ESME_RINVDLMEMBDESC( 53 ),
        ESME_RINVDLMEMBTYP( 56 ),
        ESME_RINVDLMODOPT( 57 ),
        ESME_RINVDESTFLAG( 64 ),
        ESME_RINVSUBREP( 66 ),
        ESME_RINVESMCLASS( 67 ),
        ESME_RCNTSUBDL( 68 ),
        ESME_RSUBMITFAIL( 69 ),
        ESME_RINVSRCTON( 72 ),
        ESME_RINVSRCNPI( 73 ),
        ESME_RINVDSTTON( 80 ),
        ESME_RINVDSTNPI( 81 ),
        ESME_RINVSYSTYP( 83 ),
        ESME_RINVREPFLAG( 84 ),
        ESME_RINVNUMMSGS( 85 ),
        ESME_RTHROTTLED( 88 ),
        ESME_RPROVNOTALLWD( 89 ),
        ESME_RINVSCHED( 97 ),
        ESME_RINVEXPIRY( 98 ),
        ESME_RINVDFTMSGID( 99 ),
        ESME_RX_T_APPN( 100 ),
        ESME_RX_P_APPN( 101 ),
        ESME_RX_R_APPN( 102 ),
        ESME_RQUERYFAIL( 103 ),
        ESME_RINVPGCUSTID( 128 ),
        ESME_RINVPGCUSTIDLEN( 129 ),
        ESME_RINVCITYLEN( 130 ),
        ESME_RINVSTATELEN( 131 ),
        ESME_RINVZIPPREFIXLEN( 132 ),
        ESME_RINVZIPPOSTFIXLEN( 133 ),
        ESME_RINVMINLEN( 134 ),
        ESME_RINVMIN( 135 ),
        ESME_RINVPINLEN( 136 ),
        ESME_RINVTERMCODELEN( 137 ),
        ESME_RINVCHANNELLEN( 138 ),
        ESME_RINVCOVREGIONLEN( 139 ),
        ESME_RINVCAPCODELEN( 140 ),
        ESME_RINVMDTLEN( 141 ),
        ESME_RINVPRIORMSGLEN( 142 ),
        ESME_RINVPERMSGLEN( 143 ),
        ESME_RINVPGALERTLEN( 144 ),
        ESME_RINVSMUSERLEN( 145 ),
        ESME_RINVRTDBLEN( 146 ),
        ESME_RINVREGDELLEN( 147 ),
        ESME_RINVMSGDISTLEN( 148 ),
        ESME_RINVPRIORMSG( 149 ),
        ESME_RINVMDT( 150 ),
        ESME_RINVPERMSG( 151 ),
        ESME_RINVMSGDIST( 152 ),
        ESME_RINVPGALERT( 153 ),
        ESME_RINVSMUSER( 154 ),
        ESME_RINVRTDB( 155 ),
        ESME_RINVREGDEL( 156 ),
        ESME_RINVOPTPARSTREAM( 157 ),
        ESME_ROPTPARNOTALLWD( 158 ),
        ESME_RINVOPTPARLEN( 159 ),
        ESME_RMISSINGOPTPARAM( 195 ),
        ESME_RINVOPTPARAMVAL( 196 ),
        ESME_RDELIVERYFAILURE( 254 ),
        ESME_RUNKNOWNERR( 255 ),
        ESME_LAST_ERROR( 300 );        

        private int status;

        private static Hashtable<Integer,CommandStatus> reverse =
                new Hashtable<Integer, CommandStatus>();

        CommandStatus( int status )
        {
            this.status = status;
        }

        int getStatus()
        {
            return status;
        }

        static CommandStatus find( int status )
        {
            return reverse.get( status );
        }

        static void register( CommandStatus status )
        {
            reverse.put( status.getStatus(), status );
        }
    }

    /**
     * Java won't let me populate CommandStatus.reverse from the constructors
     */
    static {
        for( CommandStatus status: CommandStatus.values() )
        {
            CommandStatus.register( status );
        }
    }

    private JComponent command;
    private JComponent status;
    private JTextField sequence = new JTextField( 30 );

    Vector<PDUTypeListener> listeners = new Vector<PDUTypeListener>();
    
    /**
     * Constructor
     * #param pdu PDU to display/edit
     * @param editFlag Allow this PDU to be edited?
     */
    public PDUHeaderPane( boolean editFlag )
    {
        setBorder( new TitledBorder( "PDU Header" ) );

        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add( new JLabel( "PDU Type: " ), constraints );
        constraints.gridx = 1;
        constraints.weightx = 0.5;
        if( editFlag )
        {
            final JComboBox commandMenu = new JComboBox();
            for( CommandID cmd : CommandID.values() )
            {
                commandMenu.addItem( cmd );
            }
            command = commandMenu;
            commandMenu.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent actionEvent )
                {
                    for( PDUTypeListener listener: listeners )
                    {
                        listener.notifyType(
                            ((CommandID)commandMenu.getSelectedItem()).getId()
                        );
                    }
                }
            } );
        }
        else
        {
            JTextField commandText = new JTextField( 30 );
            command = commandText;
        }
        add( command, constraints );

        ++constraints.gridy;
        constraints.gridx = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add( new JLabel( "Command Status: " ), constraints );
        constraints.gridx = 1;
        constraints.weightx = 0.5;
        if( editFlag )
        {
            JComboBox statusMenu = new JComboBox();
            for( CommandStatus sts : CommandStatus.values() )
            {
                statusMenu.addItem( sts );
            }
            status = statusMenu;
        }
        else
        {
            JTextField statusText = new JTextField( 30 );
            status = statusText;
        }
        add( status, constraints );

        ++constraints.gridy;
        constraints.gridx = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add( new JLabel( "Sequence Number: " ), constraints );
        constraints.weightx = 0.5;
        constraints.gridx = 1;
        sequence.setText( "1" );
        add( sequence, constraints );

        if( editFlag )
        {
            constraints.weightx = 0.5;
            constraints.gridx = 2;
            JButton incButton = new JButton( "Increment" );
            incButton.addActionListener( new ActionListener()
            {
                public void actionPerformed( ActionEvent actionEvent )
                {
                    incrementSeqNumber();
                }
            } );
            add( incButton, constraints );
        }

        command.setEnabled( editFlag );
        status.setEnabled( editFlag );
        sequence.setEditable( editFlag );
    }

    void incrementSeqNumber()
    {
        if( sequence.getText().equals( "" ) )
        {
            sequence.setText( "1" );
        }
        else
        {
            Integer seq = Integer.valueOf( sequence.getText() );
            ++seq;
            sequence.setText( seq.toString() );
        }
    }


    CommandID getCommandID()
    {
        if( command instanceof JTextField )
        {
            return CommandID.valueOf(((JTextField) command).getText());
        }
        else
        {
            return (CommandID)((JComboBox) command).getSelectedItem();
        }
    }


    CommandStatus getCommandStatus()
    {
        if( command instanceof JTextField )
        {
            return CommandStatus.valueOf(((JTextField)status).getText());
        }
        else
        {
            return (CommandStatus)((JComboBox)status).getSelectedItem();
        }
    }


    void setCommandStatus( int commandStatus )
    {
        if( status instanceof JTextField )
        {
            ((JTextField)this.status).setText(
                CommandStatus.find( commandStatus ).toString() );
        }
        else
        {
           ((JComboBox) command).setSelectedItem(
               CommandStatus.find( commandStatus ) );
        }
    }


    void setCommandId( int commandId )
    {
        if( command instanceof JTextField )
        {
            ((JTextField)this.command).setText(
               CommandID.find( commandId ).toString() );
        }
        else
        {
           ((JComboBox) command).setSelectedItem(
               CommandID.find( commandId ) );
        }
    }


    int getSequence()
    {
        return Integer.parseInt( "0" + sequence.getText(), 10 );
    }

    void setSequence( int sequence )
    {
        this.sequence.setText( Integer.toString( sequence, 10 ) );
    }

    /**
     * This listener allows other panels to be updated when a different PDU
     * type is
     * selected
     */
    protected interface PDUTypeListener
    {
        public void notifyType( int type );
    }

    /**
     * Add a listener to be notified when a different PDU
     * type is
     * selected
     * @param listener Listener to be notified when a different PDU
     * type is
     * selected
     */
    protected void addPDUTypeListener( PDUTypeListener listener )
    {
        listeners.add( listener );
    }
}
