/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml;


import com.apple.eawt.*;

/**
 * User: ffouquet
 * Date: 04/07/11
 * Time: 17:56
 */
public class MacIntegration {

    public static void addOSXIntegration(final ThingMLPanel editor){
        Application app = Application.getApplication();
        //app.setEnabledAboutMenu(true);
	    //app.setEnabledPreferencesMenu(true);
  /*
        app.setOpenURIHandler(new com.apple.eawt.OpenURIHandler() {
            @Override
            public void openURI(AppEvent.OpenURIEvent openURIEvent) {

                editor.codeEditor().setText(editor.codeEditor()+"\n"+openURIEvent.getURI());

               //System.out.println("Hi");
            }
        });
        app.setOpenFileHandler(new com.apple.eawt.OpenFilesHandler() {
            @Override
            public void openFiles(AppEvent.OpenFilesEvent openFilesEvent) {
                //System.out.println("Hi");
                editor.codeEditor().setText(editor.codeEditor()+"\n"+openFilesEvent.getFiles().size());
                editor.codeEditor().setText(editor.codeEditor()+"\n"+openFilesEvent.getSearchTerm());
            }
        });

     */
        app.addApplicationListener(new ApplicationAdapter() {
            @Override
            public void handleAbout(ApplicationEvent applicationEvent) {
                super.handleAbout(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void handleOpenApplication(ApplicationEvent applicationEvent) {
                super.handleOpenApplication(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void handleOpenFile(ApplicationEvent applicationEvent) {
                System.out.println("open => "+applicationEvent.getFilename());
                //editor.codeEditor().setText(editor.codeEditor()+"\n"+applicationEvent.getFilename());
            }

            @Override
            public void handlePreferences(ApplicationEvent applicationEvent) {
                super.handlePreferences(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void handlePrintFile(ApplicationEvent applicationEvent) {
                super.handlePrintFile(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void handleQuit(ApplicationEvent applicationEvent) {
                super.handleQuit(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void handleReOpenApplication(ApplicationEvent applicationEvent) {
                super.handleReOpenApplication(applicationEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });


    }


}
