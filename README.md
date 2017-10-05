## How to install
Go to PhpStorm/WebStorm then Settings->Plugins->Browser repositories... search for **Gherkin TS Runner** and just install it. <br/>
Second way is to downlaod and install it manually by **Install plugin from disk...**. Jar file you can get from [JetBrains Plugins Repository](https://plugins.jetbrains.com/plugin/10035-gherkin-ts-runner).

## Setup
Plugin require to fill every setting in settings to work.
![Settings](https://i.imgur.com/tPXEVJi.png)
**Feature Regex**<br/>
This regular expressions is used to find lines where **Feature** start in file with **feature** extension and then mark it.<br/>
Eg. **Feature:([a-zA-Z ]+)$**<br/><br/>
**Scenario Regex**<br/>
This regular expressions is used to find lines where **Scenario** start in file with **feature** extension and then mark it.<br/>
Eg. **Scenario:([a-zA-Z ]+)$**<br/><br/>
**Protractor cmd path**<br/>
This is path to **protractor.cmd** file which is usually located in **\node_modules\.bin\protractor.cmd**.<br/><br/>
**Protractor js config path**<br/>
This is path to protractor config file with **js** extension. If you are using typescript this path must be setted to file after transpile(js).<br/><br/>
**Features dir path**<br/>
This is path to directory which contains **feature** files.


## Usage
At the beginning of file there is a icon which will execute whole **Feature**.<br/>
In every line that match regex for **Scenario** is icon which will execute only single scenario.
![Preview](https://i.imgur.com/yiCD5Fi.png)<br/><br/>

Output of executing is in **Gherkin Runner** tab which contains bookmarks like **Run1**, **Run2** etc.
![Output](https://i.imgur.com/e5jtEcw.png)

In the end it will run protractor in command line like:<br/>
protractor full_path_to_protractor_config_js --specs=full_path_to_feature_file(when running certain scenario it will add line when it starts like ":12")
