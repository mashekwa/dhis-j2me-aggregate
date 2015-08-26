package org.hisp.dhis.mobile.view;

/*
 * Copyright (c) 2004-2013, University of Oslo All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. * Neither the name of the HISP project nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.hisp.dhis.mobile.connection.ConnectionManager;
import org.hisp.dhis.mobile.midlet.DHISMIDlet;
import org.hisp.dhis.mobile.midlet.FacilityMIDlet;
import org.hisp.dhis.mobile.ui.Text;

public class MessagingMenuView
    extends AbstractView
    implements CommandListener
{
    private static final String CLASS_TAG = "MessagingMenuView";

    private List menuList;

    private Command exitCommand;

    private FacilityMIDlet facilityMIDlet;

    public MessagingMenuView( DHISMIDlet dhisMIDlet )
    {
        super( dhisMIDlet );
        this.facilityMIDlet = (FacilityMIDlet) dhisMIDlet;
    }

    public void prepareView()
    {
        System.gc();
    }

    public void showView()
    {
        prepareView();
        this.switchDisplayable( null, this.getMenuList() );

    }

    public void commandAction( Command command, Displayable displayable )
    {
        if ( command == this.exitCommand )
        {
            dhisMIDlet.exitMIDlet();
        }
        else if ( command == List.SELECT_COMMAND )
        {
            int index = getMenuList().getSelectedIndex();
            String item = getMenuList().getString( index );

            if ( item.equals( "Feedback" ) )
            {
                dhisMIDlet.getFeedbackView().showView();
            }
            if ( item.equals( "Send Message" ) )
            {
                dhisMIDlet.getFindUserView().showView();
            }
            if ( item.equals( "Conversations" ) )
            {
                ConnectionManager.setUrl( dhisMIDlet.getCurrentOrgUnit().getDownloadMessageConversationUrl() );
                ConnectionManager.downloadMessageConversation();
            }

        }
    }

    public List getMenuList()
    {
        if ( this.menuList == null )
        {
            this.menuList = new List( "Messaging Menu", List.IMPLICIT );

            menuList.append( "Send Message", null );
            menuList.append( "Conversations", null );
            menuList.append( "Feedback", null );

            menuList.addCommand( getExitCommand() );

            menuList.setCommandListener( this );

        }

        return this.menuList;
    }

    public void setMenuList( List menuList )
    {
        this.menuList = menuList;
    }

    public Command getExitCommand()
    {
        if ( exitCommand == null )
        {
            exitCommand = new Command( Text.EXIT(), Command.EXIT, 0 );
        }
        return exitCommand;
    }

    public void setExitCommand( Command exitCommand )
    {
        this.exitCommand = exitCommand;
    }

}
