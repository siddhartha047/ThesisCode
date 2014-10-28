import sys
import os
from PyQt4.QtCore import *
from PyQt4.QtGui import *
import plots
import pyqtgraph as pg
jmetal_dir=str(os.getcwd()).split('\\')
pfdir=jmetal_dir[:-1]
pfdir.append('pf')
pfdir='\\'.join(pfdir)
print pfdir
dirctry=jmetal_dir[:-1]
dirctry.append('abcGenerations')
dirctry='\\'.join(dirctry)
print dirctry


class mainDialog(QDialog,plots.Ui_plotdialog):
    def __init__(self,parent=None):
        super(mainDialog,self).__init__(parent)
        self.setupUi(self)
        files=os.listdir(pfdir)
        files=sorted(files)
        self.current_pf=files[0]
        self.currentpos=0
        self.pfshow=True
        for i in files:
            self.comboBox.addItem(i)
        w1=self.graphicsView.addPlot()
        self.s1=pg.ScatterPlotItem(size=5)
        w1.addItem(self.s1)
        self.hslider.valueChanged.connect(self.plotgraph)
        self.comboBox.activated[str].connect(self.combo_chosen)
        self.showbtn.clicked.connect(self.handlehide)

    def handlehide(self):
        self.pfshow=not self.pfshow
        self.s1.clear()
        self.plotgraph(self.currentpos)


    def combo_chosen(self,value):
        print value
        self.current_pf=value
        with open(pfdir+'\\'+self.current_pf,'r') as f:
            ptr=self.getxyvalues(f)
            self.s1.addPoints(pen='r',pos=ptr)


    def plotgraph(self,i):
        global dirctry
        #p1=self.graphicsView.plot()
        #p1.setPen((200,200,100))
        self.currentpos=1
        p=1
        #print i
        #p=i+1
        #import numpy as np
        #x=np.random.random(10)
        #y=x*3
        #p=np.array(zip(x,y),dtype=[('x',float),('y',float)])
        #self.graphicsView.setData(p)
        self.s1.clear()
        self.hslider.setMaximum(1)
        with open(dirctry+'\\'+'pwfg8_2.pf','r') as f:
            ptr=self.getxyvalues(f)
            self.s1.addPoints(pos=ptr,pen=None)
        if self.pfshow:
            with open(pfdir+'\\'+self.current_pf,'r') as f:
                ptr=self.getxyvalues(f)
                self.s1.addPoints(pen='r',pos=ptr)

    def getxyvalues(self,filepointer):
        t=filepointer.readline()
        val=[]
        while t:
            #print t
            val.append(tuple(map(lambda x:float(x),t.strip().split())))
            t=filepointer.readline()
        return val

app = QApplication(sys.argv)
form = mainDialog()
form.show()
sys.exit(app.exec_())
