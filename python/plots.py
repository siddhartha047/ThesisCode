# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'plots.ui'
#
# Created: Thu Dec 19 11:59:56 2013
#      by: PyQt4 UI code generator 4.9.1
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_plotdialog(object):
    def setupUi(self, plotdialog):
        plotdialog.setObjectName(_fromUtf8("plotdialog"))
        plotdialog.resize(854, 610)
        self.graphicsView = GraphicsLayoutWidget(plotdialog)
        self.graphicsView.setGeometry(QtCore.QRect(30, 10, 811, 481))
        self.graphicsView.setObjectName(_fromUtf8("graphicsView"))
        self.hslider = QtGui.QSlider(plotdialog)
        self.hslider.setGeometry(QtCore.QRect(20, 510, 431, 29))
        self.hslider.setMaximum(100)
        self.hslider.setOrientation(QtCore.Qt.Horizontal)
        self.hslider.setTickPosition(QtGui.QSlider.TicksBothSides)
        self.hslider.setObjectName(_fromUtf8("hslider"))
        self.comboBox = QtGui.QComboBox(plotdialog)
        self.comboBox.setGeometry(QtCore.QRect(30, 550, 141, 31))
        self.comboBox.setObjectName(_fromUtf8("comboBox"))
        self.showbtn = QtGui.QToolButton(plotdialog)
        self.showbtn.setGeometry(QtCore.QRect(240, 550, 151, 31))
        self.showbtn.setObjectName(_fromUtf8("showbtn"))

        self.retranslateUi(plotdialog)
        QtCore.QMetaObject.connectSlotsByName(plotdialog)

    def retranslateUi(self, plotdialog):
        plotdialog.setWindowTitle(QtGui.QApplication.translate("plotdialog", "Dialog", None, QtGui.QApplication.UnicodeUTF8))
        self.showbtn.setText(QtGui.QApplication.translate("plotdialog", "Show/Hide PF", None, QtGui.QApplication.UnicodeUTF8))

from pyqtgraph import GraphicsLayoutWidget
